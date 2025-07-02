package com.example.ecommerceinventory.service;

import com.example.ecommerceinventory.dto.ItemDto;
import com.example.ecommerceinventory.enums.InventoryOperation;
import com.example.ecommerceinventory.model.Item;
import com.example.ecommerceinventory.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private ItemRepository itemRepository;

    private static final String ITEM_CACHE = "items";

    @Transactional
    @CacheEvict(value = ITEM_CACHE, key = "#item.sku")
    public ItemDto createOrUpdateItem(ItemDto itemDto) {
        Optional<Item> existingItem = itemRepository.findBySku(itemDto.getSku());
        Item savedItem;

        if (existingItem.isPresent()) {
            Item dbItem = existingItem.get();
            dbItem.setName(itemDto.getName());
            dbItem.setDescription(itemDto.getDescription());
            dbItem.setPrice(itemDto.getPrice());
            dbItem.setTotalQuantity(itemDto.getTotalQuantity());
            savedItem = itemRepository.save(dbItem);
        } else {
            Item newItem = Item.builder()
                    .sku(itemDto.getSku())
                    .name(itemDto.getName())
                    .description(itemDto.getDescription())
                    .price(itemDto.getPrice())
                    .totalQuantity(itemDto.getTotalQuantity())
                    .reservedQuantity(0)
                    .build();
            savedItem = itemRepository.save(newItem);
        }
        return itemDto;
    }


    @Transactional
    @CacheEvict(value = ITEM_CACHE, key = "#sku")
    public boolean updateInventory(String sku, int quantity, InventoryOperation operation) {
        Optional<Item> itemOpt = itemRepository.findBySkuForUpdate(sku);
        if (itemOpt.isEmpty()) {
            return false;
        }

        Item item = itemOpt.get();
        switch (operation) {
            case RESERVE:
                if (item.getTotalQuantity() - item.getReservedQuantity() >= quantity) {
                    item.setReservedQuantity(item.getReservedQuantity() + quantity);
                    itemRepository.save(item);
                    return true;
                }
                break;
            case CANCEL_RESERVATION:
                if (item.getReservedQuantity() >= quantity) {
                    item.setReservedQuantity(item.getReservedQuantity() - quantity);
                    itemRepository.save(item);
                    return true;
                }
                break;
            case ADD_STOCK:
                item.setTotalQuantity(item.getTotalQuantity() + quantity);
                itemRepository.save(item);
                return true;
        }
        return false;
    }

    @Cacheable(value = ITEM_CACHE, key = "#sku")
    public Optional<Item> getItemBySku(String sku) {
        return itemRepository.findBySku(sku);
    }

    @Cacheable(value = ITEM_CACHE, key = "#sku + '_availability'")
    public Optional<Integer> getAvailableQuantity(String sku) {
        return itemRepository.findBySku(sku)
                .map(item -> item.getTotalQuantity() - item.getReservedQuantity());
    }
}

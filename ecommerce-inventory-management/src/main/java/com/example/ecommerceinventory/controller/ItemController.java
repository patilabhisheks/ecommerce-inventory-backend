package com.example.ecommerceinventory.controller;

import com.example.ecommerceinventory.dto.GenericResponse;
import com.example.ecommerceinventory.dto.ItemDto;
import com.example.ecommerceinventory.enums.InventoryOperation;
import com.example.ecommerceinventory.exceptions.BadRequestException;
import com.example.ecommerceinventory.model.Item;
import com.example.ecommerceinventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/inventory")
public class ItemController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/items")
    public ResponseEntity<GenericResponse> createOrUpdateItem(
            @RequestBody ItemDto itemDto) {
        try {
            ItemDto savedItem = inventoryService.createOrUpdateItem(itemDto);
            return ResponseEntity.ok(new GenericResponse().success(
                    "Item Created Successfully", savedItem));
        } catch (BadRequestException e) {
            return ResponseEntity.ok(
                    new GenericResponse().failure(e.getMessage(),
                            HttpStatus.BAD_REQUEST.toString()));
        }

    }

    @GetMapping("/items/{sku}")
    public ResponseEntity<Item> getItemBySku(@PathVariable String sku) {
        Optional<Item> item = inventoryService.getItemBySku(sku);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/items/{sku}/availability")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable String sku) {
        Optional<Integer> quantity = inventoryService.getAvailableQuantity(sku);
        return quantity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/items/{sku}/reserve")
    public ResponseEntity<Boolean> reserveItem(
            @PathVariable String sku,
            @RequestParam int quantity) {
        boolean success = inventoryService.updateInventory(
                sku, quantity, InventoryOperation.RESERVE);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/items/{sku}/cancel-reservation")
    public ResponseEntity<Boolean> cancelReservation(
            @PathVariable String sku,
            @RequestParam int quantity) {
        boolean success = inventoryService.updateInventory(
                sku, quantity, InventoryOperation.CANCEL_RESERVATION);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/items/{sku}/add-stock")
    public ResponseEntity<Boolean> addStock(
            @PathVariable String sku,
            @RequestParam int quantity) {
        boolean success = inventoryService.updateInventory(
                sku, quantity, InventoryOperation.ADD_STOCK);
        return ResponseEntity.ok(success);
    }
}

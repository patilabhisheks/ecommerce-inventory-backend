package com.example.ecommerceinventory.repository;

import com.example.ecommerceinventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySku(String sku);

    @Query("SELECT i FROM Item i WHERE i.sku = :sku")
    Optional<Item> findBySkuForUpdate(@Param("sku") String sku);
}

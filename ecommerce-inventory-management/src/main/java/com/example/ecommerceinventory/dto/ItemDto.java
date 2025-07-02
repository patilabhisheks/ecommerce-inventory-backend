package com.example.ecommerceinventory.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ItemDto {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer totalQuantity;
    private Integer reservedQuantity = 0;

}

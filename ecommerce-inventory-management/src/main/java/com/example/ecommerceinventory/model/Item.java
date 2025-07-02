    package com.example.ecommerceinventory.model;

    import jakarta.persistence.*;
    import lombok.Builder;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Entity
    @Builder
    @Table(name = "items")
    public class Item {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String sku;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private Double price;

        @Column(name = "total_quantity", nullable = false)
        private Integer totalQuantity;

        @Column(name = "reserved_quantity", nullable = false)
        private Integer reservedQuantity = 0;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(Integer totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public Integer getReservedQuantity() {
            return reservedQuantity;
        }

        public void setReservedQuantity(Integer reservedQuantity) {
            this.reservedQuantity = reservedQuantity;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

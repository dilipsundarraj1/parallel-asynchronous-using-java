package com.learnjava.service;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.ProductOption;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;

public class InventoryService {
    public Inventory addInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();

    }

    public CompletableFuture<Inventory> addInventory_CF(ProductOption productOption) {

        return CompletableFuture.supplyAsync(() -> {
            delay(500);
            return Inventory.builder()
                    .count(2).build();
        });

    }
}

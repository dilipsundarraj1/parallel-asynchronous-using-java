package com.learnjava.domain.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Integer itemId;
    private String itemName;
    private double rate;
    private Integer quantity;
    private boolean isExpired;
}

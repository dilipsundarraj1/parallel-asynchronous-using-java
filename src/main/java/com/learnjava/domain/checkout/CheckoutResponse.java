package com.learnjava.domain.checkout;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutResponse {

    CheckoutStatus checkoutStatus;
    List<CartItem> errorList = new ArrayList<>();
    double finalRate;

    public CheckoutResponse(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, List<CartItem> errorList) {
        this.checkoutStatus = checkoutStatus;
        this.errorList = errorList;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, double finalRate) {
        this.checkoutStatus = checkoutStatus;
        this.finalRate = finalRate;
    }
}

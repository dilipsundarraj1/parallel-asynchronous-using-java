package com.learnjava.service;


import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.stream.Collectors.toList;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {

        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                //.stream()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceValid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceValid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(toList());
        timeTaken();

        if (priceValidationList.size() > 0) {
            log("Checkout Error");
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        log("Checkout Complete");

        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}

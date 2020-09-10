package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();

    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void parallelism() {
        // parallelism =  no of cores -1
        System.out.println("parallelism : " +ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    void no_of_cores() {
        System.out.println("no of cores : " +Runtime.getRuntime().availableProcessors());
    }

    @Test
    void checkout_6_items() {

        //given
        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate()>0);
    }

    @Test
    void checkout_12_items() {

        //given
        //-Djava.util.concurrent.ForkJoinPool.common.parallelism=100
        //  System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

        Cart cart = DataSet.createCart(12);
        System.out.println("size of the cart  : "+ cart.getCartItemList().size());
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_modify_parallelism() {

        //given
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
          System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

        Cart cart = DataSet.createCart(105);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_25_items() {

        //given
        Cart cart = DataSet.createCart(25);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}
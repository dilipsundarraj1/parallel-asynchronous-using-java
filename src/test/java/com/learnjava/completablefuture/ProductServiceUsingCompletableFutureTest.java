package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {

    ProductInfoService pis = new ProductInfoService();
    ReviewService rs = new ReviewService();
    InventoryService is = new InventoryService();
    ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(pis, rs, is);

    @Test
    void retrieveProductDetails() {

        //given
        String productId = "ABC123";
        startTimer();

        //when
        Product product = pscf.retrieveProductDetails(productId);
        System.out.println("product:  " + product);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());

    }



    @Test
    void retrieveProductDetails_CF() {

        //given
        String productId = "ABC123";
        startTimer();

        //when
        CompletableFuture<Product> cfProduct = pscf.retrieveProductDetails_CF(productId);

        //then
        cfProduct
                .thenAccept((product -> {
                    assertNotNull(product);
                    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
                    assertNotNull(product.getReview());
                }))
                .join();

        timeTaken();

    }

    @Test
    void retrieveProductDetailsWithInventory() {

        //given
        String productId = "ABC123";
        startTimer();

        //when
        Product product = pscf.retrieveProductDetailsWithInventory(productId);
        System.out.println("product:  " + product);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });


        assertNotNull(product.getReview());

    }
    @Test
    void retrieveProductDetailsWithInventory_approach2() {

        //given
        String productId = "ABC123";
        startTimer();

        //when
        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
        System.out.println("product:  " + product);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });


        assertNotNull(product.getReview());

    }
}
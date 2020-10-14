package com.learnjava.completablefuture;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {

        startTimer();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = cfProductInfo
                .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // blocks the thread
        timeTaken();
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetails_CF(String productId) {

        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        return cfProductInfo
                .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review));
    }


    public Product retrieveProductDetailsWithInventory(String productId) {

        startTimer();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply((productInfo -> {
                    productInfo.setProductOptions(updateInventoryToProductOption(productInfo));
                    return productInfo;
                }))
                .handle(((productInfo, throwable) -> {
                    log("productInfo : " + productInfo);
                    log("throwable : " + throwable);
                    return productInfo;
                }));

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = cfProductInfo
                .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // blocks the thread
        timeTaken();
        return product;
    }

    public Product retrieveProductDetailsWithInventory_approach2(String productId) {

        startTimer();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply((productInfo -> {
                    productInfo.setProductOptions(updateInventoryToProductOption_approach2(productInfo));
                    //  productInfo.setProductOptions(updateInventoryToProductOption_approach3(productInfo));
                    return productInfo;
                }));

        CompletableFuture<Review> cfReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally((ex) -> {
                    log("Handled the Exception in review Service : " + ex.getMessage());
                    return Review.builder()
                            .noOfReviews(0).overallRating(0.0)
                            .build();
                });

        Product product = cfProductInfo
                .thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review))
                .whenComplete((prod, ex) -> {
                    log("Inside whenComplete : " + prod + "and the exception is " + ex);
                    if (ex != null) {
                        log("Exception in whenComplete is : " + ex);
                    }
                })
                .join(); // blocks the thread
        timeTaken();
        return product;
    }


    private List<ProductOption> updateInventoryToProductOption(ProductInfo productInfo) {

        List<ProductOption> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                })
                .collect(Collectors.toList());

        return productOptionList;
    }

    private List<ProductOption> updateInventoryToProductOption_approach2(ProductInfo productInfo) {

        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption ->
                        CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                                .exceptionally((ex) -> {
                                    log("Exception in Inventory Service : " + ex.getMessage());
                                    return Inventory.builder()
                                            .count(1).build();
                                })
                                .thenApply((inventory -> {
                                    productOption.setInventory(inventory);
                                    return productOption;
                                })))
                .collect(Collectors.toList());

        return productOptionList.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private List<ProductOption> updateInventoryToProductOption_approach3(ProductInfo productInfo) {

        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption ->
                        CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                                .exceptionally((ex) -> {
                                    log("Exception in Inventory Service : " + ex.getMessage());
                                    return Inventory.builder()
                                            .count(1).build();
                                })
                                .thenApply((inventory -> {
                                    productOption.setInventory(inventory);
                                    return productOption;
                                })))
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(productOptionList.toArray(new CompletableFuture[productOptionList.size()]));
        return cfAllOf
                .thenApply(v->{
                    return  productOptionList.stream().map(CompletableFuture::join)
                            .collect(Collectors.toList());
                })
                .join();

    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}

package com.learnjava.thread;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.apache.commons.lang3.time.StopWatch;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);

        Thread productInfoThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfoRunnable.productInfo, reviewRunnable.review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    private class ProductInfoRunnable implements Runnable {
        private String productId;
        private ProductInfo productInfo;

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {

            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {
        private String productId;
        private Review review;
        public ReviewRunnable(String productId) {
            this.productId = productId;
        }
        public Review getReview() {
            return review;
        }
        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}

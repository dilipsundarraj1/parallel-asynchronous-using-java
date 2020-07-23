package com.learnjava.service;

import com.learnjava.domain.Review;
import com.learnjava.util.LoggerUtil;

import static com.learnjava.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        LoggerUtil.log("retrieveReviews after Delay");
        return new Review(200, 4.5);
    }
}

package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MoviesClient {

    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        var movieInfo = invokeMovieInfoService(movieInfoId);
        var reviews = invokeReviewsService(movieInfoId);
        return new Movie(movieInfo, reviews);
    }

    public List<Movie> retrieveMovieList(List<Long> movieInfoIds) {

        return movieInfoIds
                .stream()
                //.parallel()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());

    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {

        var movieInfo = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieInfoId));
        var reviews = CompletableFuture.supplyAsync(() -> invokeReviewsService(movieInfoId));
        return movieInfo.thenCombine(reviews, Movie::new);
    }

    public List<Movie> retrieveMovieList_CF(List<Long> movieInfoIds) {

        var movieFutures = movieInfoIds
                .stream()
                .parallel()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());


        return movieFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

    }

    public List<Movie> retrieveMovieList_CF_allOf(List<Long> movieInfoIds) {

        var movieFutures = movieInfoIds
                .stream()
                .parallel()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());

        var cfAllOf = CompletableFuture.allOf(movieFutures.toArray(new CompletableFuture[movieFutures.size()]));

         return cfAllOf
                .thenApply(v-> movieFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()))
                 .join();
    }

    public List<Movie> retrieveMovieList_CF_anyOf(List<Long> movieInfoIds) {

        var movieFutures = movieInfoIds
                .stream()
                .parallel()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());

        var cfAllOf = CompletableFuture.allOf(movieFutures.toArray(new CompletableFuture[movieFutures.size()]));

        return cfAllOf
                .thenApply(v-> movieFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();
    }

    private List<Review> invokeReviewsService(Long movieInfoId) {

        String uri = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toUriString();

        List<Review> reviews = webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();

        return reviews;
    }


    public MovieInfo invokeMovieInfoService(Long movieInfoId) {

        var movieInfoUrlPath = "/v1/movie_infos/{movieInfoId}";

        var movieInfo = webClient.get()
                .uri(movieInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();

        return movieInfo;

    }
}

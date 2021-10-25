package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import com.learnjava.util.CommonUtil;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MoviesClient {

    private WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        //CommonUtil.startTimer();
        var movieInfo = invokeMoviesService(movieInfoId);
        var reviews = invokeReviewsService(movieInfoId);
        //CommonUtil.timeTaken();
        return new Movie(movieInfo, reviews);

    }

    public List<Movie> retrieveMovieList(List<Long> movieInfoIds) {
        CommonUtil.startTimer();
        var moviesList = movieInfoIds
                .stream()
                .parallel()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());

        CommonUtil.timeTaken();
        return moviesList;

    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {

        var movieInfo = CompletableFuture.supplyAsync(() -> invokeMoviesService(movieInfoId));
        var reviews = CompletableFuture.supplyAsync(() -> invokeReviewsService(movieInfoId));
        return movieInfo.thenCombine(reviews, (movieInfo1, reviews1) -> {
            return new Movie(movieInfo1, reviews1);
        });
    }

    public List<CompletableFuture<Movie>> retrieveMovieList_CF(List<Long> movieInfoIds) {

        return movieInfoIds
                .stream()
                .parallel()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());
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


    public MovieInfo invokeMoviesService(Long movieInfoId) {

        var movieInfoUrlPath = "/v1//movie_infos/{movieInfoId}";

        var movieInfo = webClient.get()
                .uri(movieInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();

        return movieInfo;

    }
}

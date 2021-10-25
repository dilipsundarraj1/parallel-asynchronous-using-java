package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class MoviesClientTest {

    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();
    MoviesClient moviesClient = new MoviesClient(webClient);


    @Test
    @Order(1)
    void retrieveMovie() {
        //given
        var movieInfoId = 1L;

        //when
        var movie = moviesClient.retrieveMovie(movieInfoId);

        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;


    }

    @Test
    @Order(3)
    void retrieveMovieList() {
        //given
        var movieInfoIds = List.of(1L,2L, 3L , 4L);

        //when
        var movies = moviesClient.retrieveMovieList(movieInfoIds);
        System.out.println("movies : " + movies);

        //then
        assert movies.size() == 4;
    }

    @Test
    @Order(2)
    void retrieveMovie_CF() {
        //given
        var movieInfoId = 1L;

        //when
        CommonUtil.startTimer();
        var movie = moviesClient.retrieveMovie_CF(movieInfoId).join();
        CommonUtil.timeTaken();
        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;


    }

    @Test
    @Order(4)
    void retrieveMovieList_CF() {
        //given
        var movieInfoIds = List.of(1L,2L, 3L , 4L);

        //when
        CommonUtil.startTimer();
        var movies = moviesClient.retrieveMovieList_CF(movieInfoIds).stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println("movies : " + movies);
        CommonUtil.timeTaken();
        //then
        assert movies.size() == 4;

    }
}
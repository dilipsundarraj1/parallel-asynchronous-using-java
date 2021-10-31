package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MoviesClientTest {

    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();
    MoviesClient moviesClient = new MoviesClient(webClient);


    @BeforeEach
    void setUpMoviesClient() {
        var movieInfoId = 1L;
        moviesClient.retrieveMovie(movieInfoId);
    }

    @Test
    @RepeatedTest(10)
    void retrieveMovie() {
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;

        //when

        var movie = moviesClient.retrieveMovie(movieInfoId);


        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

        CommonUtil.timeTaken();
    }

    @Test
    @RepeatedTest(10)
    void retrieveMovie_1() {
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;

        //when

        var movie = moviesClient.retrieveMovie(movieInfoId);


        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

        CommonUtil.timeTaken();
    }

    @Test
    @RepeatedTest(10)
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
    void retrieveMovieList() {
        //given
        var movieInfoIds = List.of(1L,2L, 3L , 4L, 5L, 6L, 7L);

        //when
        CommonUtil.startTimer();
        var movies = moviesClient.retrieveMovieList(movieInfoIds);
        System.out.println("movies : " + movies);
        CommonUtil.timeTaken();

        //then
        assert movies.size() == 7;
    }


    @Test
    void retrieveMovieList_CF() {
        //given
        var movieInfoIds = List.of(1L,2L, 3L , 4L, 5L, 6L, 7L);

        //when
        CommonUtil.startTimer();
        var movies = moviesClient.retrieveMovieList_CF(movieInfoIds);

        System.out.println("movies : " + movies);
        CommonUtil.timeTaken();
        //then
        assert movies.size() == 7;

    }

    @Test
    void retrieveMovieList_CF_approach2() {
        //given
        var movieInfoIds = List.of(1L,2L, 3L , 4L, 5L, 6L, 7L);

        //when
        CommonUtil.startTimer();
        var movies = moviesClient.retrieveMovieList_CF_allOf(movieInfoIds);

        System.out.println("movies : " + movies);
        CommonUtil.timeTaken();
        //then
        assert movies.size() == 7;

    }
}
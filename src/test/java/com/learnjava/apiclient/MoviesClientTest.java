package com.learnjava.apiclient;

import com.learnjava.apiclient.MoviesClient;
import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class MoviesClientTest {


    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    MoviesClient moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie() {
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;
        //when
        var movie = moviesClient.retrieveMovie(movieInfoId);

        System.out.println("movie : " + movie);
        CommonUtil.timeTaken();
        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
    }

    @RepeatedTest(10)
    void retrieveMovies() {
        CommonUtil.startTimer();
        //given
        var movieInfoIds = List.of(1L,2L, 3L,4L,5L,6L,7L);
        //when
        var movies = moviesClient.retrieveMovies(movieInfoIds);

        System.out.println("movie : " + movies);
        CommonUtil.timeTaken();
        //then
        assert movies!=null;
        assert movies.size()==7;
    }

    @RepeatedTest(10)
    void retrieveMovieList_CF() {
        CommonUtil.startTimer();
        //given
        var movieInfoIds = List.of(1L,2L, 3L,4L,5L,6L,7L);
        //when
        var movies = moviesClient.retrieveMovieList_CF(movieInfoIds);

        System.out.println("movie : " + movies);
        CommonUtil.timeTaken();
        //then
        assert movies!=null;
        assert movies.size()==7;
    }


    @RepeatedTest(10)
    void retrieveMovie_CF() {
        CommonUtil.startTimer();
        //given
        var movieInfoId = 1L;
        //when
        var movie = moviesClient.retrieveMovie_CF(movieInfoId)
                .join();

        System.out.println("movie : " + movie);
        CommonUtil.timeTaken();
        //then
        assert movie!=null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
    }
}
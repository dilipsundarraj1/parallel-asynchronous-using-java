package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException hwcfe;



    @Test
    void helloWorld_3_async_calls_handle() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_calls_handle();

        //then
        String expectedResult = " WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

    @Test
    void helloWorld_3_async_calls_handle_2() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        //when
        String result = hwcfe.helloWorld_3_async_calls_handle();

        //then
        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

    @Test
    void helloWorld_3_async_calls_handle_checkedException() {

        //given
        when(helloWorldService.hello()).thenAnswer( answer -> new SQLException("Exception Occurred"));
        when(helloWorldService.world()).thenAnswer(answer -> new SQLException("Exception Occurred"));

        //when
        String result = hwcfe.helloWorld_3_async_calls_handle();

        //then
        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }



    @Test
    void helloWorld_3_async_calls_handle_3() {

        //given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_calls_handle();

        //then
        String expectedResult = "HELLO WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }


    @Test
    void helloWorld_3_async_calls_exceptionally() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        //when
        String result = hwcfe.helloWorld_3_async_calls_exceptionally();

        //then
        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }



    @Test
    void helloWorld_3_async_whenComplete() {

        //given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_whenComplete();

        //then
        String expectedResult = "HELLO WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }


    @Test
    void helloWorld_3_async_whenComplete_2() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_whenComplete();

        //then
        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

}
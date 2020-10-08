package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException hwcfe;


    @Test
    void helloWorld_3_async_calls_exceptionally() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_calls_exceptionally();

        //then
        String expectedResult = " WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }


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
    void helloWorld_3_async_whenComplete() {

        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = hwcfe.helloWorld_3_async_whenComplete();

        //then
        String expectedResult = " WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }
}
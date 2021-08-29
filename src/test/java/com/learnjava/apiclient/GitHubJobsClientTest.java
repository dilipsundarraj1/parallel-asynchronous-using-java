package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class GitHubJobsClientTest {


    //WebClient webClient = WebClient.create("https://jobs.github.com");

    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 10 * 1024)).build();
    WebClient webClient = WebClient.builder().baseUrl("https://jobs.github.com").exchangeStrategies(exchangeStrategies).build();
    GitHubJobsClient ghjClient = new GitHubJobsClient(webClient);


    @Test
    void invokeGithubJobsApi_withPageNumber() {

        //given
        String jobDescription = "JavaScript";
        int pageNum = 1;

        //when
        List<GitHubPosition> gitHubPositionList  = ghjClient.invokeGithubJobsApi_withPageNumber(pageNum, jobDescription);

        //then
        assertTrue(gitHubPositionList.size()>0);
    }


    @Test
    void invokeGithubJobsApi_withMultiplePageNumbers_CF() {

        //given
        String jobDescription = "Java";
        List<Integer> pageNumbers= List.of(1,2,3);
        //when
        List<GitHubPosition> gitHubPositionList  = ghjClient.invokeGithubJobsApi_withMultiplePageNumbers_CF(pageNumbers, jobDescription);

        //then
        assertTrue(gitHubPositionList.size()>0);
    }

    @Test
    void invokeGithubJobsApi_withMultiplePageNumbers() {

        //given
        String jobDescription = "Java";
        List<Integer> pageNumbers= List.of(1,2,3);
        //when
        List<GitHubPosition> gitHubPositionList  = ghjClient.invokeGithubJobsApi_withPageNumber(pageNumbers, jobDescription);

        //then
        assertTrue(gitHubPositionList.size()>0);
    }
}
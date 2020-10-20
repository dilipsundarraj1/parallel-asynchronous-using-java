package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class GitHubJobsClient {

    private WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGithubJobsApi_withPageNumber(int pageNum, String description) {
        //startTimer();

        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("search", description)
                .queryParam("page", pageNum)
                .buildAndExpand()
                .toUriString();
        System.out.println("uri  : " + uri);

        GitHubPosition[] gitHubPositions = webClient.get().uri(uri)
                .header(CONTENT_TYPE, "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GitHubPosition[].class)
                .block();

      //  timeTaken();
        return Arrays.asList(gitHubPositions);


    }

    public List<GitHubPosition> invokeGithubJobsApi_withMultiplePageNumbers_CF(List<Integer> pageList, String description) {
        startTimer();

        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageList.stream()
                .map(pageNum -> CompletableFuture.supplyAsync(() -> invokeGithubJobsApi_withPageNumber(pageNum, description)))
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(gitHubPositions.toArray(new CompletableFuture[gitHubPositions.size()]));

        List<GitHubPosition>  gitHubPositionsList =  cfAllOf
                .thenApply(v-> gitHubPositions.stream().map(CompletableFuture::join)
                        .flatMap(gitHubPositions1 -> gitHubPositions1.stream())
                        .collect(Collectors.toList()))
                .join();
        timeTaken();

        return gitHubPositionsList;


    }

    public List<GitHubPosition> invokeGithubJobsApi_withPageNumber(List<Integer> pageList, String description) {
        startTimer();

        List<GitHubPosition> gitHubPositionsList = pageList.stream()
                .map(pageNum ->  invokeGithubJobsApi_withPageNumber(pageNum, description))
                .flatMap(gitHubPositions1 -> gitHubPositions1.stream())
                .collect(Collectors.toList());
        timeTaken();
        return gitHubPositionsList;
    }


}

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

        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNum)
                .buildAndExpand()
                .toUriString();
        System.out.println("uri  : " + uri);

        List<GitHubPosition> gitHubPositions = webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();

        return gitHubPositions;


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

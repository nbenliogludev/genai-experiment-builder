package com.nbenliogludev.genaiexperimentbuilder.service.impl;

import com.nbenliogludev.genaiexperimentbuilder.service.HtmlFetcherService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * @author nbenliogludev
 */
@Service
public class HtmlFetcherServiceImpl implements HtmlFetcherService {

    private final WebClient webClient;

    public HtmlFetcherServiceImpl() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.USER_AGENT, "GenAI-Experiment-Builder/1.0")
                .build();
    }

    @Override
    public String fetchHtml(String pageUrl) {
        if (pageUrl == null || pageUrl.isBlank()) {
            throw new IllegalArgumentException("pageUrl must not be null or blank");
        }

        String html = webClient.get()
                .uri(pageUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(10));

        if (html == null || html.isBlank()) {
            throw new IllegalStateException("Fetched empty HTML from " + pageUrl);
        }

        return html;
    }
}

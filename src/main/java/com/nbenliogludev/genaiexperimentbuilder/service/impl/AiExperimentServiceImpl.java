package com.nbenliogludev.genaiexperimentbuilder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbenliogludev.genaiexperimentbuilder.ai.ExperimentPromptFactory;
import com.nbenliogludev.genaiexperimentbuilder.dto.AiExperimentLlmResponse;
import com.nbenliogludev.genaiexperimentbuilder.dto.AiGenerateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.AiGenerateExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.dto.CreateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.ExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.service.AiExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.ExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.HtmlFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author nbenliogludev
 */
@Service
@RequiredArgsConstructor
public class AiExperimentServiceImpl implements AiExperimentService {

    private final ChatClient chatClient;
    private final ExperimentService experimentService;
    private final HtmlFetcherService htmlFetcherService;
    private final ObjectMapper objectMapper;
    private final ExperimentPromptFactory experimentPromptFactory;

    @Override
    public AiGenerateExperimentResponse generateExperiment(AiGenerateExperimentRequest request) {
        String pageUrl = request.getPage();
        if (pageUrl == null || pageUrl.isBlank()) {
            throw new IllegalArgumentException("pageUrl (page) must not be null or blank");
        }

        String pageHtml = htmlFetcherService.fetchHtml(pageUrl);

        String systemPrompt = experimentPromptFactory.buildSystemPrompt();
        String userPrompt = experimentPromptFactory.buildUserPrompt(request, pageHtml);

        String content = chatClient
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        AiExperimentLlmResponse llmResponse;

        try {
            llmResponse = objectMapper.readValue(content, AiExperimentLlmResponse.class);
        } catch (JsonProcessingException e) {
            llmResponse = new AiExperimentLlmResponse();
            llmResponse.setExperimentName(request.getIdea());
            llmResponse.setVariants(Collections.emptyList());
        }

        CreateExperimentRequest createReq = new CreateExperimentRequest();
        createReq.setName(llmResponse.getExperimentName());
        createReq.setGoal(request.getGoal());
        createReq.setPage(request.getPage());

        ExperimentResponse experiment = experimentService.createExperiment(createReq);

        return AiGenerateExperimentResponse.builder()
                .experiment(experiment)
                .variants(llmResponse.getVariants())
                .rawIdea(request.getIdea())
                .build();
    }
}

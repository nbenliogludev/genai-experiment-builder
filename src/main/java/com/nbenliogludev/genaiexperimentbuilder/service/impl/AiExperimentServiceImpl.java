package com.nbenliogludev.genaiexperimentbuilder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbenliogludev.genaiexperimentbuilder.dto.*;
import com.nbenliogludev.genaiexperimentbuilder.dto.*;
import com.nbenliogludev.genaiexperimentbuilder.service.AiExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.ExperimentService;
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
    private final ObjectMapper objectMapper;

    @Override
    public AiGenerateExperimentResponse generateExperiment(AiGenerateExperimentRequest request) {
        String systemPrompt = """
                You are a senior CRO (Conversion Rate Optimization) and A/B testing expert.
                Your task is to design a clear A/B test for a web page.

                Return ONLY valid JSON, no Markdown, in the following structure:

                {
                  "experimentName": "string",
                  "variants": [
                    {
                      "name": "string",
                      "description": "string",
                      "uiChanges": "string"
                    }
                  ]
                }
                """;

        String userPrompt = """
                Create an A/B test based on the following input:

                Idea: %s
                Page: %s
                Goal: %s

                The goal is to improve UX and conversions. Propose 2-3 strong variants.
                """
                .formatted(request.getIdea(), request.getPage(), request.getGoal());

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

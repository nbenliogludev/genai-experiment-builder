package com.nbenliogludev.genaiexperimentbuilder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbenliogludev.genaiexperimentbuilder.ai.ExperimentPromptFactory;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.AiExperimentLlmResponse;
import com.nbenliogludev.genaiexperimentbuilder.dto.request.AiGenerateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.AiGenerateExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.AiGeneratedVariantDto;
import com.nbenliogludev.genaiexperimentbuilder.dto.request.CreateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.ExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.service.AiExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.ExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.HtmlFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        String pageHtml = htmlFetcherService.fetchHtml(request.getPage());

        String systemPrompt = experimentPromptFactory.buildSystemPrompt();
        String userPrompt = experimentPromptFactory.buildUserPrompt(request, pageHtml);

        String content = chatClient
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        System.out.println("LLM RAW CONTENT:\n" + content);

        AiExperimentLlmResponse llmResponse = parse(content, request);

        CreateExperimentRequest createReq = new CreateExperimentRequest();
        createReq.setName(llmResponse.getExperimentName());
        createReq.setGoal(request.getGoal());
        createReq.setPage(request.getPage());

        ExperimentResponse experiment = experimentService.createExperiment(createReq);

        List<AiGeneratedVariantDto> finalVariants = convertVariants(llmResponse);

        return AiGenerateExperimentResponse.builder()
                .experiment(experiment)
                .variants(finalVariants)
                .rawIdea(request.getIdea())
                .build();
    }

    private AiExperimentLlmResponse parse(String content, AiGenerateExperimentRequest fallback) {
        try {
            return objectMapper.readValue(content, AiExperimentLlmResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("JSON PARSE FAILED → fallback");

            AiExperimentLlmResponse r = new AiExperimentLlmResponse();
            r.setExperimentName(fallback.getIdea());
            r.setVariants(Collections.emptyList());
            return r;
        }
    }

    private List<AiGeneratedVariantDto> convertVariants(AiExperimentLlmResponse response) {
        if (response.getVariants() == null) return Collections.emptyList();

        return response.getVariants().stream()
                .map(v -> {
                    AiGeneratedVariantDto dto = new AiGeneratedVariantDto();
                    dto.setName(v.getName());
                    dto.setDescription(v.getDescription());
                    dto.setUiChanges(v.getUiChanges());
                    dto.setExplanation(v.getExplanation());
                    dto.setTrafficShare(v.getTrafficShare());

                    if (v.getModifiedHtml() != null) {
                        String base64 = Base64.getEncoder()
                                .encodeToString(v.getModifiedHtml().getBytes(StandardCharsets.UTF_8));
                        dto.setModifiedHtmlBase64(base64);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
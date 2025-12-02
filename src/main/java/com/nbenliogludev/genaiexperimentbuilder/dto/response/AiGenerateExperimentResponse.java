package com.nbenliogludev.genaiexperimentbuilder.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author nbenli...
 */
@Getter
@Builder
public class AiGenerateExperimentResponse {
    private ExperimentResponse experiment;
    private List<AiGeneratedVariantDto> variants;
    private String rawIdea;
}
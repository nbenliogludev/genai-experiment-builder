package com.nbenliogludev.genaiexperimentbuilder.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author nbenliogludev
 */
@Getter
@Builder
public class AiGenerateExperimentResponse {
    private ExperimentResponse experiment;
    private List<AiGeneratedVariantDto> variants;
    private String rawIdea;
}

package com.nbenliogludev.genaiexperimentbuilder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author nbenliogludev
 */
@Getter
@Setter
public class AiExperimentLlmResponse {

    @JsonProperty("experimentName")
    private String experimentName;

    @JsonProperty("variants")
    private List<AiGeneratedVariantDto> variants;
}

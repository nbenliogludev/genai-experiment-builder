package com.nbenliogludev.genaiexperimentbuilder.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nbenliogludev
 */
@Getter
@Setter
public class AiGenerateExperimentRequest {

    @NotBlank
    private String idea;

    @NotBlank
    private String page;

    @NotBlank
    private String goal;
}

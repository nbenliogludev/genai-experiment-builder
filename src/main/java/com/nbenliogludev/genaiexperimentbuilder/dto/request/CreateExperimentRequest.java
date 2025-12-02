package com.nbenliogludev.genaiexperimentbuilder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nbenliogludev
 */
@Getter
@Setter
public class CreateExperimentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String goal;

    @NotBlank
    private String page;
}

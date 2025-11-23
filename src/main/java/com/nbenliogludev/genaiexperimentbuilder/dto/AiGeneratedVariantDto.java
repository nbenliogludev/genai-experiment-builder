package com.nbenliogludev.genaiexperimentbuilder.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nbenliogludev
 */
@Getter
@Setter
public class AiGeneratedVariantDto {
    private String name;
    private String description;
    private String uiChanges;
}

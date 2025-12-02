package com.nbenliogludev.genaiexperimentbuilder.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Вариант, который отдаём на фронт
 * как часть AiGenerateExperimentResponse.
 *
 * @author nbenli...
 */
@Getter
@Setter
public class AiGeneratedVariantDto {
    private String name;
    private String description;
    private String uiChanges;
    private String modifiedHtmlBase64; // backend sets this
    private String explanation;
    private Double trafficShare;
}
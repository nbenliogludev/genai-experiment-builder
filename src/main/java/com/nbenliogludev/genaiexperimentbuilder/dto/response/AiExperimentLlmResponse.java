package com.nbenliogludev.genaiexperimentbuilder.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author nbenliogludev
 */
@Getter
@Setter
public class AiExperimentLlmResponse {
    private String experimentName;
    private List<VariantRaw> variants;

    @Getter
    @Setter
    public static class VariantRaw {
        private String name;
        private String description;
        private String uiChanges;
        private String modifiedHtml;
        private String explanation;
        private Double trafficShare;
    }
}
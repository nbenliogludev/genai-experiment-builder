package com.nbenliogludev.genaiexperimentbuilder.service;

import com.nbenliogludev.genaiexperimentbuilder.dto.request.AiGenerateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.AiGenerateExperimentResponse;

/**
 * @author nbenliogludev
 */
public interface AiExperimentService {

    AiGenerateExperimentResponse generateExperiment(AiGenerateExperimentRequest request);
}


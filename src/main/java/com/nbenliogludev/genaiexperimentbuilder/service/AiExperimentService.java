package com.nbenliogludev.genaiexperimentbuilder.service;

import com.nbenliogludev.genaiexperimentbuilder.dto.AiGenerateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.AiGenerateExperimentResponse;

/**
 * @author nbenliogludev
 */
public interface AiExperimentService {

    AiGenerateExperimentResponse generateExperiment(AiGenerateExperimentRequest request);
}


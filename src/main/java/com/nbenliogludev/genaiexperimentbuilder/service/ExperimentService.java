package com.nbenliogludev.genaiexperimentbuilder.service;

import com.nbenliogludev.genaiexperimentbuilder.dto.CreateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.ExperimentResponse;

import java.util.List;

/**
 * @author nbenliogludev
 */
public interface ExperimentService {

    ExperimentResponse createExperiment(CreateExperimentRequest request);

    List<ExperimentResponse> getAllExperiments();
}

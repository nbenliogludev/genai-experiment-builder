package com.nbenliogludev.genaiexperimentbuilder.service.impl;

import com.nbenliogludev.genaiexperimentbuilder.dto.CreateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.ExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.entity.Experiment;
import com.nbenliogludev.genaiexperimentbuilder.enums.ExperimentStatus;
import com.nbenliogludev.genaiexperimentbuilder.repository.ExperimentRepository;
import com.nbenliogludev.genaiexperimentbuilder.service.ExperimentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nbenliogludev
 */
@Service
@RequiredArgsConstructor
public class ExperimentServiceImpl implements ExperimentService {

    private final ExperimentRepository experimentRepository;

    @Override
    public ExperimentResponse createExperiment(CreateExperimentRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Experiment experiment = Experiment.builder()
                .name(request.getName())
                .goal(request.getGoal())
                .page(request.getPage())
                .status(ExperimentStatus.DRAFT)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Experiment saved = experimentRepository.save(experiment);
        return mapToResponse(saved);
    }

    @Override
    public List<ExperimentResponse> getAllExperiments() {
        return experimentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExperimentResponse mapToResponse(Experiment experiment) {
        return ExperimentResponse.builder()
                .id(experiment.getId())
                .name(experiment.getName())
                .goal(experiment.getGoal())
                .page(experiment.getPage())
                .status(experiment.getStatus())
                .createdAt(experiment.getCreatedAt())
                .updatedAt(experiment.getUpdatedAt())
                .build();
    }
}

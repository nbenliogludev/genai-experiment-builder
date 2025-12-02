package com.nbenliogludev.genaiexperimentbuilder.controller;

import com.nbenliogludev.genaiexperimentbuilder.dto.request.AiGenerateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.AiGenerateExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.dto.response.ExperimentResponse;
import com.nbenliogludev.genaiexperimentbuilder.service.AiExperimentService;
import com.nbenliogludev.genaiexperimentbuilder.service.ExperimentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nbenliogludev
 */
@RestController
@RequestMapping("/api/experiments")
@RequiredArgsConstructor
public class ExperimentController {

    private final ExperimentService experimentService;
    private final AiExperimentService aiExperimentService;

    @GetMapping
    public ResponseEntity<List<ExperimentResponse>> getAllExperiments() {
        List<ExperimentResponse> experiments = experimentService.getAllExperiments();
        return ResponseEntity.ok(experiments);
    }

    @PostMapping("/ai-generate")
    public ResponseEntity<AiGenerateExperimentResponse> aiGenerateExperiment(
            @Valid @RequestBody AiGenerateExperimentRequest request
    ) {
        AiGenerateExperimentResponse response = aiExperimentService.generateExperiment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

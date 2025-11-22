package com.nbenliogludev.genaiexperimentbuilder.controller;

import com.nbenliogludev.genaiexperimentbuilder.dto.CreateExperimentRequest;
import com.nbenliogludev.genaiexperimentbuilder.dto.ExperimentResponse;
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

    @PostMapping
    public ResponseEntity<ExperimentResponse> createExperiment(
            @Valid @RequestBody CreateExperimentRequest request
    ) {
        ExperimentResponse response = experimentService.createExperiment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExperimentResponse>> getAllExperiments() {
        List<ExperimentResponse> experiments = experimentService.getAllExperiments();
        return ResponseEntity.ok(experiments);
    }
}

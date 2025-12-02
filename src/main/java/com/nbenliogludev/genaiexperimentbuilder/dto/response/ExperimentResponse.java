package com.nbenliogludev.genaiexperimentbuilder.dto.response;

import com.nbenliogludev.genaiexperimentbuilder.enums.ExperimentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author nbenliogludev
 */
@Getter
@Builder
public class ExperimentResponse {

    private Long id;
    private String name;
    private String goal;
    private String page;
    private ExperimentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

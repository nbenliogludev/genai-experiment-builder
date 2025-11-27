package com.nbenliogludev.genaiexperimentbuilder.repository;

import com.nbenliogludev.genaiexperimentbuilder.entity.ExperimentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author nbenliogludev
 */
public interface ExperimentEventRepository extends JpaRepository<ExperimentEvent, Long> {

    List<ExperimentEvent> findByExperimentId(Long experimentId);

    List<ExperimentEvent> findByExperimentIdAndVariantId(Long experimentId, Long variantId);
}

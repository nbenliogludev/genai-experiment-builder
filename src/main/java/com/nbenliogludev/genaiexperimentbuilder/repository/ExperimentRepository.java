package com.nbenliogludev.genaiexperimentbuilder.repository;

import com.nbenliogludev.genaiexperimentbuilder.entity.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author nbenliogludev
 */
public interface ExperimentRepository extends JpaRepository<Experiment, Long> {
}

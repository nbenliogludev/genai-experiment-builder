package com.nbenliogludev.genaiexperimentbuilder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author nbenliogludev
 */
@Entity
@Table(name = "variants")
@Getter
@Setter
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experiment_id", nullable = false)
    private Experiment experiment;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "ui_changes", columnDefinition = "text")
    private String uiChanges;

    @Lob
    @Column(name = "modified_html", columnDefinition = "text")
    private String modifiedHtml;

    @Column(name = "traffic_share")
    private BigDecimal trafficShare;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
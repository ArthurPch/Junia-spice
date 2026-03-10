package com.jad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class MachineTool extends AbstractEntity {
    private Integer id;
    private String label;
    private LocalTime installationDuration;
    private LocalTime cleaningDuration;
    private Integer minQuantity;
    private Integer maxQuantity;

    public MachineTool(final Integer id, final String label, final LocalTime installationDuration,
                       final LocalTime cleaningDuration,
                       final Integer minQuantity, final Integer maxQuantity) {
        this.id = id;
        this.label = label;
        this.installationDuration = installationDuration;
        this.cleaningDuration = cleaningDuration;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }
}

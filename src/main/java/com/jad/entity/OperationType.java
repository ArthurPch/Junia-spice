package com.jad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class OperationType extends AbstractEntity {
    private Integer id;
    private String label;
    private Byte minNbComponents;
    private Byte maxNbComponents;
    private LocalTime duration;
    private Integer lossOfQuantity;
    private Integer idProductType;

    public OperationType(final Integer id, final String label, final Byte minNbComponents, final Byte maxNbComponents,
                         final LocalTime duration,
                         final Integer lossOfQuantity, final Integer idProductType) {
        this.id = id;
        this.label = label;
        this.minNbComponents = minNbComponents;
        this.maxNbComponents = maxNbComponents;
        this.duration = duration;
        this.lossOfQuantity = lossOfQuantity;
        this.idProductType = idProductType;
    }
}
package com.jad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductType  extends AbstractEntity{
    private Integer id;
    private String label;

    public ProductType(final Integer id, final String label) {
        this.id = id;
        this.label = label;
    }
}
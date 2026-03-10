package com.jad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product extends AbstractEntity {
    private Integer id;
    private String label;
    private String createdBy;
    private Integer idTypeProduct;
    private Boolean isAtomic;

    public Product(final Integer id,
                   final String label,
                   final String createdBy,
                   final Integer idTypeProduct,
                   final Boolean isAtomic) {
        this.id = id;
        this.label = label;
        this.createdBy = createdBy;
        this.idTypeProduct = idTypeProduct;
        this.isAtomic = isAtomic;
    }
}
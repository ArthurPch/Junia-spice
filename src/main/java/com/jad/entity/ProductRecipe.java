package com.jad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRecipe  extends AbstractEntity{
    public ProductRecipe(final Integer idProduct, final Integer idOperationType) {
        this.idProduct = idProduct;
        this.idOperationType = idOperationType;
    }

    private Integer idProduct;
    private Integer idOperationType;
}
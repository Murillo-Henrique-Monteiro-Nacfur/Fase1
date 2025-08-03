package com.postech.fiap.fase1.core.validation.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;

public interface ProductCreateValidation {
    void validation(ProductDomain productDomain);
}

package com.dekinci.gdeploy.demo.demoshopproduct

import com.dekinci.gdeploy.demo.api.mapping.ProductMapping
import com.dekinci.gdeploy.demo.api.model.ProductDto
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController : ProductMapping {
    override fun getProduct(id: String): ProductDto {
        return ProductDto("Product-v3-$id")
    }
}
package com.dekinci.gdeploy.demo.demoshopproduct

import com.dekinci.gdeploy.demo.api.mapping.ProductMapping
import com.dekinci.gdeploy.demo.api.model.ProductDto
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class ProductController : ProductMapping {
    override fun getProduct(id: String): ProductDto {
        return ProductDto("Product-v1-$id", Random.nextInt(10, 10000))
    }
}
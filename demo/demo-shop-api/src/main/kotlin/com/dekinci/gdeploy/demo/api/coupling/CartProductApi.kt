package com.dekinci.gdeploy.demo.api.coupling

import com.dekinci.gdeploy.apimarkup.RestApi
import com.dekinci.gdeploy.demo.api.mapping.ProductMapping

@RestApi("demo-shop-product", version = "v1")
class CartProductApi(
    val productMapping: ProductMapping
)
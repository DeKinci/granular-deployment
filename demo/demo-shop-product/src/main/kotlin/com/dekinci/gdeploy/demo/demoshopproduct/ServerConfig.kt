package com.dekinci.gdeploy.demo.demoshopproduct

import com.dekinci.gdeploy.demo.api.coupling.CartProductApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServerConfig {
    @Bean
    fun remoteProductApi(productController: ProductController) = CartProductApi(productController)
}
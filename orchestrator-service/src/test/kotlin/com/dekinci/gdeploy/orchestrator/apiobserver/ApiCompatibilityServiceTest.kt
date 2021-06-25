package com.dekinci.gdeploy.orchestrator.apiobserver

import io.swagger.v3.oas.models.OpenAPI
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.web.client.RestTemplate

internal class ApiCompatibilityServiceTest {

    @Test
    fun checkCompatibility() {
        val server = fetchOpenApi("http://gd-shop.i.dknc.io/product/meta/api/server/CartProductApi")
        val client = fetchOpenApi("http://gd-shop.i.dknc.io/cart/meta/api/client/CartProductApi")

        assert(ApiCompatibilityService().checkCompatibility(client, server))
    }

    @Test
    fun checkDifferent() {
    }

    private fun fetchOpenApi(uri: String): OpenAPI {
        return RestTemplate().getForObject(uri, OpenAPI::class.java)!!
    }
}
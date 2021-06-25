package com.dekinci.gdeploy.orchestrator.outrouting

import com.dekinci.gdeploy.orchestrator.CloudService
import com.dekinci.gdeploy.orchestrator.KubernetesConfig
import org.junit.jupiter.api.Test

internal class EnvoyFilterServiceTest {

    @Test
    fun routeSource() {
        val service = ClientRoutingService(KubernetesConfig().namespace(), KubernetesConfig().istioClient())
        service.routeSource(CloudService("demo-shop-cart", "v1"), CloudService("demo-shop-product", "v2"))
    }
}
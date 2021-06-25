package com.dekinci.gdeploy.orchestrator

import io.fabric8.kubernetes.client.ConfigBuilder
import me.snowdrop.istio.client.DefaultIstioClient
import org.junit.jupiter.api.Test
import java.util.*

internal class VirtualServiceRouteServiceTest {

    @Test
    fun routeAll() {
        val istio = DefaultIstioClient(
            ConfigBuilder()
                .withMasterUrl("https://10.0.11.1:6443")
                .withCaCertData(k8sCA)
                .withOauthToken(Base64.getDecoder().decode(k8sAdminToken).decodeToString())
                .build()
        )
        VirtualServiceRouteService("gd-demo-shop", istio).routeAll()

    }

    @Test
    fun route() {
    }
}
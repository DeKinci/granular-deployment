package com.dekinci.gdeploy.orchestrator.deployment

import com.dekinci.gdeploy.orchestrator.k8sAdminToken
import com.dekinci.gdeploy.orchestrator.k8sCA
import com.dekinci.gdeploy.orchestrator.resName
import io.fabric8.kubernetes.client.ConfigBuilder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class DefaultGranularClientTest {

    @Test
    fun v1alpha1VirtualDeployment() {
        val client = DefaultGranularClient(
            ConfigBuilder()
                .withMasterUrl("https://10.0.11.1:6443")
                .withCaCertData(k8sCA)
                .withOauthToken(Base64.getDecoder().decode(k8sAdminToken).decodeToString())
                .build()
        )

        client.v1alpha1VirtualDeployment().inAnyNamespace().list().items.forEach {
            println("${it.resName()} replicas: ${it.getSpec().getDeployment().replicas}")
        }
    }
}
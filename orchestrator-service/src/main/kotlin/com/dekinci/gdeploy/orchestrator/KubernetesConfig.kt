package com.dekinci.gdeploy.orchestrator

import com.dekinci.gdeploy.orchestrator.deployment.DefaultGranularClient
import com.dekinci.gdeploy.orchestrator.deployment.GranularClient
import io.fabric8.kubernetes.client.Config
import io.fabric8.kubernetes.client.ConfigBuilder
import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.fabric8.kubernetes.client.KubernetesClient
import me.snowdrop.istio.client.DefaultIstioClient
import me.snowdrop.istio.client.IstioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KubernetesConfig {

    @Bean
    fun kubernetesClient(): KubernetesClient {
        return DefaultKubernetesClient(
            ConfigBuilder()
                .withMasterUrl("https://10.0.11.1:6443")
                .withCaCertData(k8sCA)
                .withOauthToken(Base64.getDecoder().decode(k8sAdminToken).decodeToString())
                .build()
        )
    }

    @Bean
    fun istioClient(): IstioClient {
        return DefaultIstioClient(
            ConfigBuilder()
                .withMasterUrl("https://10.0.11.1:6443")
                .withCaCertData(k8sCA)
                .withOauthToken(Base64.getDecoder().decode(k8sAdminToken).decodeToString())
                .build()
        )
    }

    @Bean
    fun granularClient(): GranularClient {
        return DefaultGranularClient(
            ConfigBuilder()
                .withMasterUrl("https://10.0.11.1:6443")
                .withCaCertData(k8sCA)
                .withOauthToken(Base64.getDecoder().decode(k8sAdminToken).decodeToString())
                .build()
        )
    }

    @Bean
    fun namespace(): String {
        return "gd-demo-shop"
    }
}
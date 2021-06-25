package com.dekinci.gdeploy.orchestrator.deployment

import io.fabric8.kubernetes.api.KubernetesResourceMappingProvider
import io.fabric8.kubernetes.api.model.KubernetesResource

class GranularResourceMappingProvider : KubernetesResourceMappingProvider {
    private val mappings: Map<String, Class<out KubernetesResource>> = mapOf(
        "granular-deployment.dekinci.com/v1alpha1#VirtualDeployment" to VirtualDeployment::class.java
    )

    override fun getMappings(): Map<String, Class<out KubernetesResource>> {
        return mappings
    }
}
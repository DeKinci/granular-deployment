package com.dekinci.gdeploy.orchestrator

import me.snowdrop.istio.client.IstioClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DeployableServiceLocator(
    val istioClient: IstioClient,
    val namespace: String
) {
    fun checkRouting() {
        val services = listDestinationsByHost()

        for (service in services) {
            for (version in service.value) {

            }
        }
    }

    private fun getOfferedApi() {

    }

    private fun getRequestedApis() {
        RestTemplate()
    }

    private fun listDestinationsByHost(): Map<String, List<String>> {
        return istioClient.v1beta1DestinationRule().inNamespace(namespace).list().items
            .associate { it.spec.host to it.spec.subsets.map { it.name } }
    }
}
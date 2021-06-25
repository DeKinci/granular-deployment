package com.dekinci.gdeploy.demo.demoshopcart

import com.dekinci.gdeploy.demo.api.coupling.CartProductApi
import com.dekinci.gdeploy.integration.RemoteClientFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig {
    @Bean
    fun remoteTestRemote(remoteClientFactory: RemoteClientFactory): CartProductApi =
        remoteClientFactory.createClient()
}
package com.dekinci.gdeploy.orchestrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["com.dekinci.gdeploy"])
class OrchestratorServiceApplication

fun main(args: Array<String>) {
    runApplication<OrchestratorServiceApplication>(*args)
}

package com.dekinci.gdeploy.orchestrator.apiobserver

import com.dekinci.gdeploy.orchestrator.CloudService
import org.springframework.data.annotation.Id

data class ApiVersion(
    @Id
    val id: String?,
    val service: CloudService
)

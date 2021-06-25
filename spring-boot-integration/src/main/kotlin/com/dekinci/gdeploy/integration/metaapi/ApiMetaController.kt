package com.dekinci.gdeploy.integration.metaapi

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletRequest

@RestController
@Hidden
@RequestMapping("/meta/api")
class ApiMetaController(private val apiMetaHolder: ApiMetaHolder) {

    @GetMapping("client")
    fun listClients(request: HttpServletRequest): List<String> {
        return apiMetaHolder.clientMappings.keys.toList()
    }

    @GetMapping("client/{name}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getClient(request: HttpServletRequest, @PathVariable name: String): String {
        val selfUri = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString()

        val api = apiMetaHolder.clientMappings[name]!!
        return api.second.openapiJson(request, selfUri)
    }

    @GetMapping("server")
    fun listServers(request: HttpServletRequest): List<String> {
        return apiMetaHolder.serverMapping.keys.toList()
    }

    @GetMapping("server/{name}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getServer(request: HttpServletRequest, @PathVariable name: String): String {
        val selfUri = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString()

        val api = apiMetaHolder.serverMapping[name]!!
        return api.second.openapiJson(request, selfUri)
    }
}
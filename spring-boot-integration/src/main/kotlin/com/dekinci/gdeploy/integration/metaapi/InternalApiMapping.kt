package com.dekinci.gdeploy.integration.metaapi

data class InternalApiMapping(val clazz: Class<*>, val host: String, val name: String = clazz.simpleName)

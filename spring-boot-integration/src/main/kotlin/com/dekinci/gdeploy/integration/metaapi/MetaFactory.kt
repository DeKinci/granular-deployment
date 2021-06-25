package com.dekinci.gdeploy.integration.metaapi

import io.swagger.v3.oas.models.OpenAPI
import org.mockito.Mockito
import org.springdoc.core.*
import org.springdoc.core.customizers.OpenApiBuilderCustomizer
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.webmvc.api.OpenApiWebMvcResource
import org.springdoc.webmvc.core.RouterFunctionProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.format.support.FormattingConversionService
import org.springframework.stereotype.Component
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider
import java.lang.reflect.Proxy
import java.util.*

@Component
class MetaFactory(
    @Qualifier("mvcContentNegotiationManager") private val contentNegotiationManager: ContentNegotiationManager,
    @Qualifier("mvcConversionService") private val conversionService: FormattingConversionService,
    @Qualifier("mvcResourceUrlProvider") private val resourceUrlProvider: ResourceUrlProvider,
    private val enableWebMvcConfiguration: WebMvcAutoConfiguration.EnableWebMvcConfiguration,
    private val openAPI: Optional<OpenAPI>?,
    private val context: ApplicationContext,
    private val securityParser: SecurityService?,
    private val openApiBuilderCustomisers: Optional<MutableList<OpenApiBuilderCustomizer>>?,
    private val requestBuilder: AbstractRequestService?,
    private val responseBuilder: GenericResponseService?,
    private val operationParser: OperationService?,
    private val actuatorProvider: Optional<ActuatorProvider?>?,
    private val springDocConfigProperties: SpringDocConfigProperties?,
    private val operationCustomizers: Optional<List<OperationCustomizer?>?>?,
    private val openApiCustomisers: Optional<List<OpenApiCustomiser?>?>?,
    private val springSecurityOAuth2Provider: Optional<SecurityOAuth2Provider?>?,
    private val routerFunctionProvider: Optional<RouterFunctionProvider?>?,
    private val repositoryRestResourceProvider: Optional<RepositoryRestResourceProvider?>?
) {
    fun createOpenApiForClient(clientClass: Class<*>): OpenApiWebMvcResource {
        val clientMappings = clientClass.constructors.first().parameters
            .map { parameter -> parameter.type }
            .toTypedArray()

        val namedFakeControllers = clientMappings.map { mapping ->
            val mname = mapping.simpleName

            val virtualControllerIdentity = Any()

            mname to Proxy.newProxyInstance(
                clientClass.classLoader,
                arrayOf(mapping, PseudoController::class.java)
            ) { proxy, method, args ->
                return@newProxyInstance when (method.name) {
                    "toString" -> mname
                    "equals" -> proxy == args[0]
                    "hashCode" -> virtualControllerIdentity.hashCode()
                    else -> null
                }
            }
        }.toList()


        val springMvcHandlerMapping = enableWebMvcConfiguration.requestMappingHandlerMapping(
            contentNegotiationManager,
            conversionService,
            resourceUrlProvider
        )

        val detectHandlerMethods = AbstractHandlerMethodMapping::class.java
            .getDeclaredMethod("detectHandlerMethods", Any::class.java)
        detectHandlerMethods.trySetAccessible()

        namedFakeControllers.onEach {
            detectHandlerMethods.invoke(springMvcHandlerMapping, it.second)
        }

        val fakeControllerMap = namedFakeControllers.associate { it.first to it.second }

        val openapi = OpenApiWebMvcResource(
            {
                val contextMock = Mockito.mock(ApplicationContext::class.java)
                Mockito.`when`(contextMock.getBeansWithAnnotation(Mockito.any())).thenReturn(emptyMap())
                Mockito.`when`(contextMock.containsBean(Mockito.any())).thenReturn(false)
                Mockito.`when`(contextMock.getBean(PropertyResolverUtils::class.java))
                    .thenAnswer { context.getBean(PropertyResolverUtils::class.java) }

                val c = OpenAPIService::class.java.declaredConstructors.first()
                c.trySetAccessible()
                val api = c.newInstance(
                    openAPI,
                    contextMock,
                    securityParser,
                    springDocConfigProperties,
                    openApiBuilderCustomisers
                ) as OpenAPIService
                api.mappingsMap.clear()
                api.cachedOpenAPI = null
                api.resetCalculatedOpenAPI()
                api.mappingsMap.putAll(fakeControllerMap)
                api
            },
            requestBuilder,
            responseBuilder,
            operationParser,
            springMvcHandlerMapping,
            actuatorProvider,
            operationCustomizers,
            openApiCustomisers,
            springDocConfigProperties,
            springSecurityOAuth2Provider,
            routerFunctionProvider,
            repositoryRestResourceProvider
        )

        return openapi
    }
}
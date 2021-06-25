package com.dekinci.gdeploy.demo.demoshopcart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.dekinci.gdeploy"])
class DemoShopCartApplication

fun main(args: Array<String>) {
    runApplication<DemoShopCartApplication>(*args)
}

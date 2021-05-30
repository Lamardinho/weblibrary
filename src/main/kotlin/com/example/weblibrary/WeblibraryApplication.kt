package com.example.weblibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
class WeblibraryApplication

@Bean
fun locale(): Locale {
    return Locale.getDefault()
}

fun main(args: Array<String>) {
    runApplication<WeblibraryApplication>(*args)
    println(appStarted())
    println("http://127.0.0.1:8085/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/")
}

fun appStarted(): String {
    return """  
 ▄▄▄·  ▄▄▄· ▄▄▄·    .▄▄ · ▄▄▄▄▄ ▄▄▄· ▄▄▄  ▄▄▄▄▄▄▄▄ .·▄▄▄▄  
▐█ ▀█ ▐█ ▄█▐█ ▄█    ▐█ ▀. •██  ▐█ ▀█ ▀▄ █·•██  ▀▄.▀·██▪ ██ 
▄█▀▀█  ██▀· ██▀·    ▄▀▀▀█▄ ▐█.▪▄█▀▀█ ▐▀▀▄  ▐█.▪▐▀▀▪▄▐█· ▐█▌
▐█ ▪▐▌▐█▪·•▐█▪·•    ▐█▄▪▐█ ▐█▌·▐█ ▪▐▌▐█•█▌ ▐█▌·▐█▄▄▌██. ██ 
 ▀  ▀ .▀   .▀        ▀▀▀▀  ▀▀▀  ▀  ▀ .▀  ▀ ▀▀▀  ▀▀▀ ▀▀▀▀▀•                                                                                         
"""
}

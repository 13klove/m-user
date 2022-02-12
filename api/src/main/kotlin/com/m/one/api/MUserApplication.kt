package com.m.one.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.m.one")
class MUserApplication

fun main(args: Array<String>) {
    runApplication<MUserApplication>(*args)
}

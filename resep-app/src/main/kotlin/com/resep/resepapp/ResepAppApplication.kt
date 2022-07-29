package com.resep.resepapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ResepAppApplication

fun main(args: Array<String>) {
	runApplication<ResepAppApplication>(*args)
}

package com.synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SyncoApplication

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)
}

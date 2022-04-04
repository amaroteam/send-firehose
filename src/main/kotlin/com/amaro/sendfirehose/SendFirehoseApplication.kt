package com.amaro.sendfirehose

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SendFirehoseApplication

fun main(args: Array<String>) {
	runApplication<SendFirehoseApplication>(*args)
}

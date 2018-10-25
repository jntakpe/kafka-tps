package com.github.jntakpe.kafka.tps

import com.github.jntakpe.kafka.tps.KafkaConfig.MAX_EVENTS
import com.github.jntakpe.kafka.tps.KafkaConfig.TOPIC
import com.github.jntakpe.kafka.tps.KafkaConfig.consumer
import com.github.jntakpe.kafka.tps.KafkaConfig.log
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

fun main(args: Array<String>) {
    var count = 0
    val consumer = KafkaConsumer<String, String>(consumer())
    consumer.subscribe(listOf(TOPIC))
    while (count < MAX_EVENTS) {
        consumer
                .poll(Duration.ofMillis(100)).onEach { log.info("Received key : ${it.key()}, value: ${it.value()}") }
                .forEach { _ -> count++ }
    }
}
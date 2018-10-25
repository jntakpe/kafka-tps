package com.github.jntakpe.kafka.tps

import com.github.jntakpe.kafka.tps.KafkaConfig.KEY
import com.github.jntakpe.kafka.tps.KafkaConfig.MAX_EVENTS
import com.github.jntakpe.kafka.tps.KafkaConfig.TOPIC
import com.github.jntakpe.kafka.tps.KafkaConfig.latch
import com.github.jntakpe.kafka.tps.KafkaConfig.producer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import reactor.core.publisher.Flux
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val latch = latch()
    val producer = KafkaProducer<String, String>(producer())
    Flux.range(0, MAX_EVENTS)
            .map { producer.send(ProducerRecord<String, String>(TOPIC, KEY, "$it")) }
            .subscribe { latch.countDown() }
    latch.await(10, TimeUnit.SECONDS)
    producer.close()
}
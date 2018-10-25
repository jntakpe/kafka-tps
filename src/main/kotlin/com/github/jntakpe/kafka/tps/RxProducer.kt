package com.github.jntakpe.kafka.tps

import com.github.jntakpe.kafka.tps.KafkaConfig.KEY
import com.github.jntakpe.kafka.tps.KafkaConfig.MAX_EVENTS
import com.github.jntakpe.kafka.tps.KafkaConfig.TOPIC
import com.github.jntakpe.kafka.tps.KafkaConfig.producer
import org.apache.kafka.clients.producer.ProducerRecord
import reactor.core.publisher.Flux
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import reactor.kafka.sender.SenderRecord
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    val latch = KafkaConfig.latch()
    val ref = AtomicInteger()
    val sender = KafkaSender.create(SenderOptions.create<String, String>(producer()))
    val records = Flux.range(1, MAX_EVENTS)
            .map { ProducerRecord(TOPIC, KEY, "$it") }
            .map { SenderRecord.create<String, String, Int>(it, ref.getAndIncrement()) }
    sender.send(records).subscribe { latch.countDown() }
    latch.await(10, TimeUnit.SECONDS)
    sender.close()
}
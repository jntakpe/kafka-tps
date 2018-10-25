package com.github.jntakpe.kafka.tps

import com.github.jntakpe.kafka.tps.KafkaConfig.consumer
import com.github.jntakpe.kafka.tps.KafkaConfig.latch
import com.github.jntakpe.kafka.tps.KafkaConfig.log
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val latch = latch()
    val receiver = KafkaReceiver.create(ReceiverOptions.create<String, String>(consumer()))
    val disposable = receiver.receive().subscribe {
        log.info("Received key : ${it.key()}, value: ${it.value()}")
        it.receiverOffset().acknowledge()
        latch.countDown()
    }
    latch.await(10, TimeUnit.SECONDS)
    disposable.dispose()
}
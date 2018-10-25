package com.github.jntakpe.kafka.tps

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CountDownLatch

object KafkaConfig {

    const val TOPIC = "hello_world_topic"
    const val KEY = "key"
    const val MAX_EVENTS = 1000
    val log: Logger = LoggerFactory.getLogger(KafkaConfig::class.java)
    private const val KAFKA_BOOTSTRAP_SERVER = "localhost:9092"

    fun consumer(): Properties {
        val props = Properties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = KAFKA_BOOTSTRAP_SERVER
        props[ConsumerConfig.GROUP_ID_CONFIG] = "testgroup"
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "true"
        props[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = "1000"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return props
    }

    fun producer(): Properties {
        val props = Properties()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KAFKA_BOOTSTRAP_SERVER
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return props
    }

    fun latch() = CountDownLatch(MAX_EVENTS)
}
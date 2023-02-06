package xyz.bogeum.sendmail

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@EnableKafka
@Configuration
class KafkaConfig {

    @Value("\${kafka-broker-ip}")
    private final lateinit var kafkaBrokerIp: String

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> =
        ConcurrentKafkaListenerContainerFactory<String, String>().also {
            it.setConcurrency(2)
            it.consumerFactory = DefaultKafkaConsumerFactory(getConfig())
            it.containerProperties.pollTimeout = 500
        }

//    @Bean
//    fun kafkaTemplate(): KafkaTemplate<String, String> =
//        KafkaTemplate(DefaultKafkaProducerFactory(producerConfig()))
//
//    private fun producerConfig() =
//        mapOf(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "${secret.kafkaBrokerIp}:9092",
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
//        )

    private fun getConfig(): Map<String, Any> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "$kafkaBrokerIp:9092",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
}
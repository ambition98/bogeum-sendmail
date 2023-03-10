package xyz.bogeum.sendmail

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val sendMailService: SendMailService
) {

    private final val log = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(id = "mem", topics = ["topic1"], clientIdPrefix = "memClientId", groupId = "group1")
    fun consume(msg: String) {
        log.info("Consumed msg: $msg")
        val sp = msg.split(" ")
        try {
            sendMailService.sendVerifyMail(sp[0], sp[1])
        } catch (e: Exception) {
            log.warn("Fail to Sendmail: ${e.message}, email: ${sp[0]}, code: ${sp[1]}")
            return
        }
    }
}
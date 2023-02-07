package xyz.bogeum.sendmail

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BogeumSendmailApplication

fun main(args: Array<String>) {
    runApplication<BogeumSendmailApplication>(*args)
}

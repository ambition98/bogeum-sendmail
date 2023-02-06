package xyz.bogeum.sendmail

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Component
class SendMailService {

    private val log = LoggerFactory.getLogger(SendMailService::class.java)
    @Value("\${server-email}")
    private final lateinit var serverEmail: String
//    = "bogeum991@gmail.com"

    @Value("\${server-password}")
    private final lateinit var serverPassword: String
//    = "gvgiaggommbjxkvp"

    fun sendVerifyMail(email: String, code: String) {
        log.info("password: $serverPassword")
        log.info("user: $serverEmail")
        log.info("Try send mail to \"$email\"")
        val props = Properties().also {
            it["mail.smtp.host"] = "smtp.gmail.com"
            it["mail.smtp.port"] = 465
            it["mail.smtp.auth"] = "true"
            it["mail.smtp.ssl.enable"] = "true"
            it["mail.smtp.ssl.trust"] = "smtp.gmail.com"
        }

        val session = Session.getDefaultInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(serverEmail, serverPassword)
            }
        })

        val html = "<h2>인증번호: $code</h2>"
        val msg = MimeMessage(session).also {
            it.setFrom(InternetAddress(serverEmail))
            it.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            it.subject = "보금자리 회원가입 인증번호입니다."
            it.setText("인증코드: $code")
            it.setContent(html, "text/html; charset=utf-8")
        }

        Transport.send(msg)
        log.info("Succeed send mail to \"$email\"")
    }
}
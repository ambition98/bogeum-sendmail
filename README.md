# bogeum-sendmail
개인프로젝트인 Bogeum-backend 서비스에서 파생된 마이크로 서비스 입니다.
Bogeum 메인 서비스에서 Kafka 메시지를 발행하면 Bogeum-sendmail 서비스에서 소비하여
메시지 정보를 보고 해당 이메일로 회원가입 인증 코드를 발송합니다.

package com.webapp.lora;



import com.webapp.lora.util.Mailer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoraApplication {

	public static void main(String[] args) {
		Logger log = Logger.getLogger(LoraApplication.class);
	//	log.error("TEST *******");
		log.warn("TEST *******");
		log.trace("TEST *******");
		log.warn("TEST *******");
		log.trace("TEST *******");
		log.warn("TEST *******");
		log.trace("TEST *******");log.warn("TEST *******");
		log.trace("TEST *******");
		log.warn("TEST *******");
		log.trace("TEST *******");

		System.out.println("TESTI --------------------------");
		SpringApplication.run(LoraApplication.class, args);
	//	log.error("TEST *******");
		log.warn("TEST *******");
		log.trace("TEST *******");
		System.out.println("TESTI --------------------------");
		new Mailer().logMail("This mail should be sent");
	}
}

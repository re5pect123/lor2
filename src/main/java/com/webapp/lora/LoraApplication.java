package com.webapp.lora;



import com.webapp.lora.util.Mailer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoraApplication {

	public static void main(String[] args) throws InterruptedException {
		Logger log = Logger.getLogger(LoraApplication.class);

		SpringApplication.run(LoraApplication.class, args);

		for (;;){
			log.info("test");
			Thread.sleep(5000);
		}

	}
}

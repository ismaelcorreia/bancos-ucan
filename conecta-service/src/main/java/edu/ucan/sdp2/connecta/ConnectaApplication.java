package edu.ucan.sdp2.connecta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"edu.ucan.sdp2.conectacore.service", "edu.ucan.sdp2.connecta.messages"})
public class ConnectaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectaApplication.class, args);
	}

}

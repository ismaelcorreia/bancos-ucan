package edu.ucan.sdp2.bancoengenhariaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({
        "edu.ucan.sdp2.bancocore.commons",
        "edu.ucan.sdp2.bancocore.entities", })
@EnableJpaRepositories({"edu.ucan.sdp2.bancocore.repositories"})
@ComponentScan({
        "edu.ucan.sdp2.bancocore.configuration.messages",
        "edu.ucan.sdp2.bancocore.utils",
        "edu.ucan.sdp2.bancocore.configuration.messages.infobip",
        "edu.ucan.sdp2.bancocore.configuration.messages.infobip",
        "edu.ucan.sdp2.bancocore.configuration.security",
        "edu.ucan.sdp2.bancocore.mapper",
        "edu.ucan.sdp2.bancocore.services",
        "edu.ucan.sdp2.conectacore.service",
        "edu.ucan.sdp2.conectacore.banco",
        "edu.ucan.sdp2.bancocore.bootstrap",
        "edu.ucan.sdp2.bancocore.controllers",
        "edu.ucan.sdp2.bancocore.controllers.publico",

})
public class BancoEngenhariaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancoEngenhariaServiceApplication.class, args);
    }

}

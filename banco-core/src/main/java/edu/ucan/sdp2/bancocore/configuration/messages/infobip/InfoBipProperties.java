package edu.ucan.sdp2.bancocore.configuration.messages.infobip;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("info-bip")
@Getter
@Setter
@NoArgsConstructor
public class InfoBipProperties {
    private String baseUrl;
    private String apiKey;
}

package edu.ucan.sdp2.bancoengenhariaservice.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ManipuladorContaUtil {

    @Value("${banco.identificador.iban}")
    @Getter
    private String iban;
    public String formatarNumeroConta(long numero) {
        return String.format("%010d", numero);
    }
    public String formatarIBANConta(long numero) {
        return String.format("%010d", numero);
    }
}

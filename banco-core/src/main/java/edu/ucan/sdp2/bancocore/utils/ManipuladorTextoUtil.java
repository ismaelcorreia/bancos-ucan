package edu.ucan.sdp2.bancocore.utils;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ManipuladorTextoUtil {

    final static int DIGITS = 6;
    public static String gerarDigitosAleatorios() {

        final Random random = new Random();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < DIGITS; i++) {
            int digito = random.nextInt(10);
            sb.append(digito);
        }

        return sb.toString();
    }


    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    public static final String getNumberFomatted(double number) {
        return new DecimalFormat("#.##0,00").format(number);
    }
    public static String dateTimeNowYYYMMddHHmmss() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")
                .withZone(ZoneId.systemDefault());
        return  formatter.format(Instant.now());
    }

    public static String generatePassword(int tamanho) {
        StringBuilder pwd = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(CARACTERES.length());
            pwd.append(CARACTERES.charAt(index));
        }
        return pwd.toString();
    }

    public static String generatePassword12() {
        StringBuilder senha = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CARACTERES.length());
            senha.append(CARACTERES.charAt(index));
        }
        return senha.toString();
    }
}

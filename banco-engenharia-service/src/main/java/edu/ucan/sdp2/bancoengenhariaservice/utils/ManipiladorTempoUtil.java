package edu.ucan.sdp2.bancoengenhariaservice.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ManipiladorTempoUtil {

    public static  boolean isTempoMaior(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        long diferencaEmMinutos = ChronoUnit.MINUTES.between(dateTime1, dateTime2);
        return Math.abs(diferencaEmMinutos) >= 1;
    }

}

package edu.ucan.sdp2.conectacore.utils;

public class BancoUtil {

    public static  String extrairIBAN(String texto) {
        return texto.substring(0, 5);
    }
}

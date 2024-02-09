package edu.ucan.sdp2.conectacore.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncriptadorTransacaoConecta {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static String encriptar(String chaveString, String texto) throws Exception {
        SecretKey chave = gerarChave(chaveString);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] textoEncriptado = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoEncriptado);
    }

    public static String desencriptar(String chaveString, String textoEncriptado) throws Exception {
        SecretKey chave = gerarChave(chaveString);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] textoDesencriptado = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
        return new String(textoDesencriptado);
    }

    private static SecretKey gerarChave(String chaveString) throws Exception {
        byte[] chaveBytes = chaveString.getBytes();
        SecretKeySpec chaveKeySpec = new SecretKeySpec(chaveBytes, ALGORITHM);
        return chaveKeySpec;
    }
}

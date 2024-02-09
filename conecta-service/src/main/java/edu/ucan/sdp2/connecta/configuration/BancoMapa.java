package edu.ucan.sdp2.connecta.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BancoMapa {
    ENG("banco-engenharia-topico","banco-engenharia-topico-falha","mysecretkey12345"),
    DIR("banco-direito-topico","banco-direito-topico-falha","mysecretkey12345"),
    ECO("banco-economia-topico","banco-economia-topico-falha","mysecretkey12345"),
    LIXEIRA("conecta-lixeira","",""),;

    private final String topico;
    private final String topicoFalha;
    private final String chave;

    public static BancoMapa fromTopico(String topico) {
        for (BancoMapa bancosTopico: BancoMapa.values()){
            if (topico.equalsIgnoreCase(bancosTopico.getTopico()))
                return bancosTopico;
        }
        return LIXEIRA;
    }

    // Chaves
//    Chave 1: K20K76BB5Kw=
//    Chave 2: hMmWt3SiF5E=
//    Chave 3: 2m/5+CCXl28=
//    Chave 4: bCf5LSJFC4Q=
//    Chave 5: zGbZVd5iG6o=
//    Chave 6: 5vXeQhGcMa8=
//    Chave 7: YHqR5RnQxHw=
//    Chave 8: cbrm67Rpjz8=
//    Chave 9: 0tS3a4wzgTc=
//    Chave 10: gDus3QgXzx4=
}

package edu.ucan.sdp2.connecta.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BancoMapa {
    ENG("banco-engenharia-topico","banco-engenharia-topico-falha","d0b3a17e2d977ec9c865cc459480fbec23f0716cda70b261da529df407e62329"),
    DIR("banco-direito-topico","banco-direito-topico-falha","e06ef8010b8cf9e00f0efb2dc8d905052a9a4a28b60a09f2e929facd8dbb01c6"),
    ECO("banco-economia-topico","banco-economia-topico-falha","7a93d4c204dc47b61207380ff7d6f8ec94a35d3d9d207180f5f7d699eb58c55c"),
    LIXEIRA("conecta-lixeira","",""),;

    private final String topico;
    private final String topicoFalha;
    private final String chave;

    public static BancoMapa fromTopico(String topico) {
        for (BancoMapa bancosTopico: BancoMapa.values()){
            if (topico.toUpperCase().startsWith(bancosTopico.getTopico()))
                return bancosTopico;
        }
        return LIXEIRA;
    }

    // Chaves
//    Chave 1: d0b3a17e2d977ec9c865cc459480fbec23f0716cda70b261da529df407e62329
//    Chave 2: e06ef8010b8cf9e00f0efb2dc8d905052a9a4a28b60a09f2e929facd8dbb01c6
//    Chave 3: 7a93d4c204dc47b61207380ff7d6f8ec94a35d3d9d207180f5f7d699eb58c55c
//    Chave 4: 83f16132e6881bf8af0709a57fe06b6a34001e924301a4c013e4a82e0bb24233
//    Chave 5: 654080d2f63f198ee69c25e53f0144c76daee7cc0d839101787d17f1d803f2c3
//    Chave 6: d30ccf4f7c5cbe81c93b57a01f823a3ff7e0759ff90458d2f7fc7c7757f8b0b0
//    Chave 7: 4bea92a4b99832d8b4404d78541877b16e2eb57b04a2a163a50cfc1d169317f4
//    Chave 8: 72584990b62d09b2c6fa4a7c831eb0a10efbaa9f5051df5d4cd9edbc66b20dbd
//    Chave 9: f9e0e8c6599df28260b3f3520c5b7d43126f0dfe73a996fe65b31aa54134ee1c
//    Chave 10: c34f8ffca56e25de94600524a9b23d6a49991e85f96bc37de102848b43964c59


}

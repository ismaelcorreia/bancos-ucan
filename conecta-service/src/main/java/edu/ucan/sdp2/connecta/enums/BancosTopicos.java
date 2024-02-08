package edu.ucan.sdp2.connecta.enums;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BancosTopicos {
    
    BANCO_ENGENHARIA("ENG"),
    BANCO_DIREITO("DIR"),
    BANCO_CONTABILIDADE("CONT");
    private final String topico;
}

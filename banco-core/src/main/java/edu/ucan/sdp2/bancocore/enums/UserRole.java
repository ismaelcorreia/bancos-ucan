package edu.ucan.sdp2.bancocore.enums;

public enum UserRole {
    Role_Administrador("Administrador"),
    Role_Operador("Empresa"),
    Role_Cliente("Cliente");

    private final String descricao;

    UserRole(String description){
        this.descricao = description;
    }

    public String getDescricao() {
        return descricao;
    }
}

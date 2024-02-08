package edu.ucan.sdp2.bancoengenhariaservice.enums;

public enum Status {
    DEACTIVE("Desativado"),
    ACTIVE("Ativo"),
    CREATED("Criado"),
    SUSPENSED("Suspenso"),
    DELETED("Deletado"),
    BLOCKED("Bloqueado"),
    DONE("Feito"),
    USED("usado"),
    IN_PROGRESS("Em progresso"),
    IN_USE("Em uso"),
    PAID("Pago"),
    EXPIRED("Expirado"),
    PENDING("Pendente"),
    STOPPED("Parado");
    private String name;

    Status(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

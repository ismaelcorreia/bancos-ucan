package edu.ucan.sdp2.connecta.dto.banco;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BancoCadastroRequisicao {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    @NotBlank(message = "O campo da sigla é obrigatório")
    private String sigla;
    @NotBlank(message = "O campo codigoIBAN é obrigatório")
    private String codigoIBAN;
    @NotBlank(message = "O campo chave é obrigatório e deve conter 14 caracteres, incluindo letras, números e caracteres especiais.")
    private String chave;

    @NotBlank(message = "O campo url é obrigatório e deve conter 14 caracteres, incluindo letras, números e caracteres especiais.")
    private String url;

    public boolean validateChave() {
        if (chave == null || chave.length() < 14 ||  chave.length() >22) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : chave.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasLetter && hasDigit;
    }


}

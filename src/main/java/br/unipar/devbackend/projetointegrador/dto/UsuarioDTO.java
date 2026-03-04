package br.unipar.devbackend.projetointegrador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class UsuarioDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O email deve ser em um formato válido")
    @NotBlank(message = "O email é obrigatório")
    private String email;


    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = " A senha deve ter no mínimo 8 caracteres, " +
                    "com letra maiúscula, número e caractere especial"
    )
    private String senha;
}

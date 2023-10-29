package br.com.sondercs.gestao_vagas.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data //Loombok depedency
public class CandidateEntity {

    private UUID id;
    private String name;

    @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [username] contem carácteres invalidos.")
    private String username;

    @Email // Validação de Email
    private String email;

    @Length(min = 10, max = 100)
    private String password;
    private String description;
    private String curriculum;

}

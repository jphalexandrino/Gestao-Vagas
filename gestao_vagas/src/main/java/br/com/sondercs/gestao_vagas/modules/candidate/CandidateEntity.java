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

    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
    private String username;

    @Email // Validação de Email
    private String email;

    @Length(min = 10, max = 100, message = "O Campo [password] deve conter entre 10 e 100 carácteres")
    private String password;
    private String description;
    private String curriculum;

}

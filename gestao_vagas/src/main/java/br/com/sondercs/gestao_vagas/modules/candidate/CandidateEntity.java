package br.com.sondercs.gestao_vagas.modules.candidate;

import java.util.UUID;

import lombok.Data;

@Data //Loombok depedency
public class CandidateEntity {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String description;
    private String curriculum;


}

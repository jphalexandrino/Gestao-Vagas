package br.com.sondercs.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuário já cadastrado");
    }
}

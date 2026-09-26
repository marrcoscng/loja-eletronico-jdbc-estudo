package br.com.loja_geral.controller;

import br.com.loja_geral.exception.*;
import br.com.loja_geral.model.Cliente;
import br.com.loja_geral.model.Email;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.service.ServiceLogin;

public class LoginController {

    private final ServiceLogin serviceLogin;

    public LoginController(ServiceLogin serviceLogin){
        this.serviceLogin = serviceLogin;
    }

    public void cadastrar(String nome, String cpf, String email, String senha){
        try{
            Usuario user = new Cliente(nome,cpf,new Email(email),senha);
            serviceLogin.serviceCadastro(user);

        }catch(DBException e){
            System.err.println("Erro em Query para o banco de dados"+e.getMessage());
        }
    }

    public Usuario autenticar(String email, String senha){
        return serviceLogin.autenticar(email,senha);
    }
}

package br.com.loja_geral.controller;

import br.com.loja_geral.exception.*;
import br.com.loja_geral.model.Cliente;
import br.com.loja_geral.model.Email;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.service.ServiceLogin;

public class LoginController {

    public void cadastrar(String nome, String cpf, String email, String senha){
        try{
            Usuario user = new Cliente(nome,cpf,new Email(email),senha);

            ServiceLogin serviceLogin = new ServiceLogin();
            serviceLogin.serviceCadastro(user);

        }catch(EmailExistenteException e){
            System.err.println(e.getMessage());
        }catch(UsuarioJaCadastradoException e){
            System.err.println("Usuaário já cadastrado: "+e.getMessage());
        }catch(DocumentoInvalidoException e){
            System.err.println("Erro "+ e.getMessage());
        }catch(EmailException e){
            System.err.println("Erro: "+e.getMessage());
        }catch(DBException e){
            System.err.println("Erro em Query para o banco de dados"+e.getMessage());
        }

    }
}

package br.com.loja_geral;


import br.com.loja_geral.controller.LoginController;
import br.com.loja_geral.model.Email;


public class AppLoja {
    public static void main(String[] args) {

        LoginController loginController = new LoginController();

        loginController.cadastrar("Juliano","18999432288","@gmail.com","SenhaFraca","CLIENTE");


    }
}






























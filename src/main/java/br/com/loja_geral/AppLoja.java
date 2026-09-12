package br.com.loja_geral;


import br.com.loja_geral.controller.LoginController;
import br.com.loja_geral.model.Email;


public class AppLoja {
    public static void main(String[] args) {

        LoginController loginController = new LoginController();

        loginController.cadastrar("Thiago dos Santos Oliveira","10295914076","thiago.so@gmail.com","SenhaFraca");


    }
}






























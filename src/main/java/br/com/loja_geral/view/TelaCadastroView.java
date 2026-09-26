package br.com.loja_geral.view;

import br.com.loja_geral.service.ServiceLogin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import br.com.loja_geral.controller.LoginController;

public class TelaCadastroView implements Tela {

    private final StackPane container;
    private final TextField txtNome;
    private final TextField txtCpf;
    private final TextField txtEmail;
    private final PasswordField txtSenha;

    private final LoginController  loginController;

    public TelaCadastroView(Navegador navegador) {

        this.loginController = new LoginController(new ServiceLogin());

        Label titleLabel = new Label("Criar Conta");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1A202C;");

        txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome completo");
        txtNome.setMaxWidth(Double.MAX_VALUE);

        txtCpf = new TextField();
        txtCpf.setPromptText("000.000.000-00");
        txtCpf.setMaxWidth(Double.MAX_VALUE);

        txtEmail = new TextField();
        txtEmail.setPromptText("seu@email.com");
        txtEmail.setMaxWidth(Double.MAX_VALUE);

        txtSenha = new PasswordField();
        txtSenha.setPromptText("••••••••");
        txtSenha.setMaxWidth(Double.MAX_VALUE);

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);
        btnCadastrar.setStyle("-fx-background-color: #FF9900; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;");

        Button btnVoltar = new Button("Voltar para o Login");
        btnVoltar.setMaxWidth(Double.MAX_VALUE);
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: #2B6CB0; -fx-cursor: hand;");

        VBox form = new VBox(12,
                new Label("Nome Completo"), txtNome,
                new Label("CPF"), txtCpf,
                new Label("E-mail"), txtEmail,
                new Label("Senha"), txtSenha,
                btnCadastrar, btnVoltar
        );

        VBox card = new VBox(18, titleLabel, form);
        card.setMaxWidth(400);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4);");

        container = new StackPane(card);
        container.setAlignment(Pos.CENTER);
        VBox.setVgrow(container, Priority.ALWAYS);

        btnVoltar.setOnAction(e -> navegador.irPara(TelaLoginView.class, () -> new TelaLoginView(navegador)));

        btnCadastrar.setOnAction(event -> {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            loginController.cadastrar(nome,cpf,email,senha);
        });
    }

    @Override
    public Node getRoot() {
        return container;
    }

    @Override
    public void aoExibir() {
        txtNome.clear();
        txtCpf.clear();
        txtEmail.clear();
        txtSenha.clear();
        txtNome.requestFocus();
    }
}
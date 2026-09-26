package br.com.loja_geral.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TelaLoginView implements Tela {

    private final StackPane container;
    private final TextField txtEmail;
    private final PasswordField passSenha;

    public TelaLoginView(Navegador navegador) {
        Label titleLabel = new Label("Acessar Conta");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1A202C;");

        Label labelEmail = new Label("E-mail");
        labelEmail.setStyle("-fx-font-weight: bold; -fx-text-fill: #4A5568;");
        txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu e-mail");
        txtEmail.setMaxWidth(Double.MAX_VALUE);

        Label labelSenha = new Label("Senha");
        labelSenha.setStyle("-fx-font-weight: bold; -fx-text-fill: #4A5568;");
        passSenha = new PasswordField();
        passSenha.setPromptText("Digite sua senha");
        passSenha.setMaxWidth(Double.MAX_VALUE);

        VBox emailBox = new VBox(5, labelEmail, txtEmail);
        VBox senhaBox = new VBox(5, labelSenha, passSenha);

        Button botaoLogin = new Button("Login");
        botaoLogin.setMaxWidth(Double.MAX_VALUE);
        botaoLogin.setStyle("-fx-background-color: #FF9900; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;");

        Button botaoCadastrar = new Button("Cadastrar");
        botaoCadastrar.setMaxWidth(Double.MAX_VALUE);
        botaoCadastrar.setStyle("-fx-background-color: #E2E8F0; -fx-text-fill: #2D3748; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;");

        // Faz os botões crescerem proporcionalmente
        HBox.setHgrow(botaoLogin, Priority.ALWAYS);
        HBox.setHgrow(botaoCadastrar, Priority.ALWAYS);

        HBox botoesBox = new HBox(15, botaoLogin, botaoCadastrar);
        botoesBox.setAlignment(Pos.CENTER);

        // Card central do Login
        VBox card = new VBox(18, titleLabel, emailBox, senhaBox, botoesBox);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(400); // Largura máxima do card para não esticar infinitamente em monitores UltraWide
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4);");

        // Permite expandir mantendo o alinhamento no centro da janela
        container = new StackPane(card);
        container.setAlignment(Pos.CENTER);
        VBox.setVgrow(container, Priority.ALWAYS);

        // Ações
        botaoCadastrar.setOnAction(e -> navegador.irPara(TelaCadastroView.class, () -> new TelaCadastroView(navegador)));
    }

    @Override
    public Node getRoot() {
        return container;
    }

    @Override
    public void aoExibir() {
        txtEmail.clear();
        passSenha.clear();
        txtEmail.requestFocus();
    }
}
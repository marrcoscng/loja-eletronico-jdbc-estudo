package br.com.loja_geral.view;

import br.com.loja_geral.controller.LoginController;
import br.com.loja_geral.model.Cliente;
import br.com.loja_geral.model.Email;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.service.ServiceLogin;
import br.com.loja_geral.util.SessaoUsuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TelaLoginView implements Tela {

    Usuario usuario = SessaoUsuario.getUsuarioLogado();

    private final VBox root;
    private final LoginController loginController;

    public TelaLoginView(Navegador navegador) {
        this.loginController = new LoginController(new ServiceLogin());


        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F4F6F8;");

        VBox cardLogin = new VBox(12);
        cardLogin.setMaxWidth(380);
        cardLogin.setPadding(new Insets(25));
        cardLogin.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label lblTitulo = new Label("Entrar na sua Conta");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0F1111;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu e-mail");
        txtEmail.setStyle("-fx-padding: 10px;");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        txtSenha.setStyle("-fx-padding: 10px;");

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setStyle("-fx-background-color: #FF9900; -fx-text-fill: #0F1111; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand; -fx-background-radius: 4px;");

        Hyperlink linkCadastro = new Hyperlink("Não tem uma conta? Cadastre-se");
        linkCadastro.setStyle("-fx-text-fill: #0066C0; -fx-underline: false;");
        linkCadastro.setOnAction(e -> navegador.irPara(TelaCadastroView.class, () -> new TelaCadastroView(navegador)));


        // ==========================================
        // LÓGICA DO BOTÃO LOGIN
        // ==========================================
        btnEntrar.setOnAction(event -> {
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText();

            // 1. Validação básica de campos
            if (email.isEmpty() || senha.isEmpty()) {
                exibirAlerta(Alert.AlertType.WARNING, "Campos Obrigatórios", "Por favor, preencha o e-mail e a senha.");
                return;
            }

            try {
                // 2. Busca e valida o usuário no banco de dados
                SessaoUsuario.encerrarSessao();
                SessaoUsuario.iniciarSessao(loginController.autenticar(email,senha));


                // 4. Redireciona para o Dashboard do Cliente
                navegador.irPara(
                        TelaClienteDashboardView.class,
                        () -> new TelaClienteDashboardView(navegador)
                );


            } catch (IllegalArgumentException e) {
                // Tratamento de credenciais incorretas
                exibirAlerta(Alert.AlertType.ERROR, "Falha no Login", e.getMessage());
            } catch (Exception e) {
                // Tratamento de falha de conexão com o banco MySQL
                exibirAlerta(Alert.AlertType.ERROR, "Erro no Sistema", "Não foi possível conectar ao banco de dados: " + e.getMessage());
            }
        });

        cardLogin.getChildren().addAll(
                lblTitulo,
                new Label("E-mail"), txtEmail,
                new Label("Senha"), txtSenha,
                btnEntrar,
                linkCadastro
        );

        root.getChildren().add(cardLogin);
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void aoExibir() {
        // Limpa os campos quando a tela é carregada
        SessaoUsuario.encerrarSessao();
    }
}
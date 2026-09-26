package br.com.loja_geral.view;

import br.com.loja_geral.exception.DocumentoInvalidoException;
import br.com.loja_geral.exception.EmailException;
import br.com.loja_geral.exception.EmailExistenteException;
import br.com.loja_geral.exception.UsuarioJaCadastradoException;
import br.com.loja_geral.model.Cliente;
import br.com.loja_geral.model.Email;
import br.com.loja_geral.util.SessaoUsuario;
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
            try {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String email = txtEmail.getText();
                String senha = txtSenha.getText();
                loginController.cadastrar(nome, cpf, email, senha);

                SessaoUsuario.iniciarSessao(new Cliente(nome,cpf,new Email(email),senha));
                    navegador.irPara(
                            TelaClienteDashboardView.class,
                            () -> new TelaClienteDashboardView(navegador)
                    );

            }catch(EmailExistenteException e){
                System.err.println(e.getMessage());
                exibirMensagem(Alert.AlertType.WARNING,"Aviso",e.getMessage());

            }catch(UsuarioJaCadastradoException e){
                System.err.println("Usuaário já cadastrado: "+e.getMessage());
                exibirMensagem(Alert.AlertType.WARNING,"Aviso",e.getMessage());

            }catch(DocumentoInvalidoException e){
                System.err.println("Erro "+ e.getMessage());
                exibirMensagem(Alert.AlertType.ERROR,"Aviso",e.getMessage());

            }catch(EmailException e){
                System.err.println("Erro: "+e.getMessage());
                exibirMensagem(Alert.AlertType.WARNING,"Aviso",e.getMessage());

            }


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

    /**
     * Exibe uma caixa de diálogo nativa do JavaFX.
     *
     * @param tipo    Tipo do alerta (WARNING, ERROR, INFORMATION, CONFIRMATION)
     * @param titulo  Texto da barra de título da janela
     * @param mensagem Conteúdo da mensagem exibida para o usuário
     */
    private void exibirMensagem(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // Remove o cabeçalho secundário para um visual mais limpo
        alert.setContentText(mensagem);
        alert.showAndWait(); // Bloqueia a tela até o usuário clicar em "OK"
    }
}
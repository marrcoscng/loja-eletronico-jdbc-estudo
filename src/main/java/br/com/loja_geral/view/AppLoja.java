package br.com.loja_geral.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLoja extends Application {

    @Override
    public void start(Stage primaryStage) {
        Navegador navegador = new Navegador();

        // Inicializa na tela de Login
        navegador.irPara(TelaLoginView.class, () -> new TelaLoginView(navegador));

        // Cria a cena definindo largura e altura iniciais
        Scene scene = new Scene(navegador, 1024, 720);

        primaryStage.setTitle("BrasilVendas - E-commerce");
        primaryStage.setScene(scene);

        // Evita que a janela encolha além do suportado
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Garante a limpeza da sessão ao fechar no 'X'
        primaryStage.setOnCloseRequest(
                e -> System.exit(0));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
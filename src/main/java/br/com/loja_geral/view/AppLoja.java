package br.com.loja_geral.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLoja extends Application {

    @Override
    public void start(Stage primaryStage) {
        Navegador navegador = new Navegador();

        // Define o login como a tela inicial
        navegador.irPara(TelaLoginView.class, () -> new TelaLoginView(navegador));

        Scene scene = new Scene(navegador, 550, 750);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Amazon");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
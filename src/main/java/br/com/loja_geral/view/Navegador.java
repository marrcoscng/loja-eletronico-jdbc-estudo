package br.com.loja_geral.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Navegador extends BorderPane {

    private final Map<Class<? extends Tela>, Tela> cache = new HashMap<>();

    public Navegador() {
        // --- 1. A LOGO É CONFIGURADA AQUI (No topo do Navegador) ---
        ImageView logoView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream("/imagens/logo-amazon-512.png"));
            logoView.setImage(image);
            logoView.setFitWidth(120); // Ajuste a largura da logo
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Imagem da logo não encontrada em /imagens/logo-amazon-512.png");
        }

        HBox topo = new HBox(logoView);
        topo.setAlignment(Pos.TOP_LEFT);
        topo.setPadding(new Insets(20, 0, 0, 20));

        // Fixa o topo com a logo na estrutura principal
        this.setTop(topo);
        this.setStyle("-fx-background-color: #F7FAFC;");
    }

    @SuppressWarnings("unchecked")
    public <T extends Tela> void irPara(Class<T> classeTela, Supplier<T> construtor) {
        Tela tela = cache.computeIfAbsent(classeTela, k -> construtor.get());
        this.setCenter(tela.getRoot());
        tela.aoExibir();
    }
}
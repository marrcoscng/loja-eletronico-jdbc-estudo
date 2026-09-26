package br.com.loja_geral.view;

import javafx.scene.Node;

public interface Tela {

    Node getRoot();
    default void aoExibir() {}

}

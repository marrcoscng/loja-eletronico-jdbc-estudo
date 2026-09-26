package br.com.loja_geral.util;

import br.com.loja_geral.model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogadoSystem = null;

    public static void iniciarSessao(Usuario usuarioLogado) {
        usuarioLogadoSystem = usuarioLogado;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogadoSystem;
    }

    public static void encerrarSessao() {
        usuarioLogadoSystem = null;
    }

    public static boolean isEstaLogado() {
        return usuarioLogadoSystem != null;
    }
}
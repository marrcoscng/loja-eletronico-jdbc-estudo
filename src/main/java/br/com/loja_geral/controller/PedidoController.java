package br.com.loja_geral.controller;

import br.com.loja_geral.exception.CarrinhoVazioException;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.UsuarioNaoEncontradoException;
import br.com.loja_geral.model.Carrinho;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.service.ServicePedido;

public class PedidoController {


    public void salvarPedido(Long idCliente, Carrinho carrinho){

        try {
            Pedido pedido = carrinho.finalizarCompra(idCliente);
            ServicePedido servicePedido = new ServicePedido();
            servicePedido.salvarPedido(pedido);

        }catch(UsuarioNaoEncontradoException e){
            System.err.println(e.getMessage());
        }catch(CarrinhoVazioException e){
            System.err.println("Error carrinho - "+e.getMessage());
        }catch(DBException e){
            System.err.println("Error - "+e.getMessage());
        }
    }


}

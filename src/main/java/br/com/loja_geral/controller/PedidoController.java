package br.com.loja_geral.controller;

import br.com.loja_geral.exception.CarrinhoVazioException;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.UsuarioNaoEncontradoException;
import br.com.loja_geral.model.Carrinho;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.service.ServicePedido;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class PedidoController {

    private final ServicePedido servicePedido;

    public PedidoController(ServicePedido servicePedido){
        this.servicePedido = new ServicePedido();
    }


    public void salvarPedido(Long idCliente, Carrinho carrinho){

        try {
            Pedido pedido = carrinho.finalizarCompra(idCliente);
            servicePedido.salvarPedido(pedido);

        }catch(UsuarioNaoEncontradoException e){
            System.err.println(e.getMessage());
        }catch(CarrinhoVazioException e){
            System.err.println("Error carrinho - "+e.getMessage());
        }catch(DBException e){
            System.err.println("Error - "+e.getMessage());
        }
    }

    public Map<UUID,Pedido> listaDePedidosPorCLienteId(Long idCliente){
        try{
            return servicePedido.listaDePedidosPorClienteId(idCliente);
        }catch(DBException e){
            System.err.println("Erro - "+e.getMessage());
            return Collections.emptyMap();
        }
    }


}

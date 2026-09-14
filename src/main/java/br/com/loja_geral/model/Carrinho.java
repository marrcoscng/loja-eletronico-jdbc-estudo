package br.com.loja_geral.model;

import br.com.loja_geral.exception.CarrinhoVazioException;
import br.com.loja_geral.exception.UsuarioNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    List<ItemPedido> listaDeItens = new ArrayList<>();

    public void addItem(ItemPedido itemPedido){

        if(itemPedido == null || itemPedido.getProduto()==null){
            return;
        }
        for(ItemPedido itens : listaDeItens){
            if(itens.getProduto().compareTo(itemPedido.getProduto()) == 0){
                itens.adicionarQuantidade(itemPedido.getQuantidade());
                return;
            }
        }
        listaDeItens.add(itemPedido);
    }

    public void esvaziarCarrinho(){
        listaDeItens.clear();
    }

    public void excluirItem(Long id){
        if(id == null){
            return;
        }
        listaDeItens.removeIf(x -> x.getProduto().getId().equals(id));
    }

    public void alterarQuantidadeDeItens(Long idProduto, Integer quantidade){
        if(quantidade == null || idProduto == null){
            return;
        }
        if(quantidade<=0){
            excluirItem(idProduto);
            return;
        }
        for(ItemPedido itens : listaDeItens){
            if(itens.getProduto()!=null && itens.getProduto().getId().equals(idProduto)){
                itens.setQuantidade(quantidade);
                return;
            }
        }
    }

    public Pedido finalizarCompra(Long idCliente){
        if(idCliente == null){
            throw new UsuarioNaoEncontradoException("Dados do cliente devem ser informados!");
        }
        if(listaDeItens == null || listaDeItens.isEmpty()){
            throw new CarrinhoVazioException("Carrinho vazio!");
        }
        Pedido pedido = new Pedido(idCliente, new ArrayList<>(listaDeItens));
        listaDeItens.clear();
        return pedido;
    }

}

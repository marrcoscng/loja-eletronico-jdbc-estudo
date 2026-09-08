package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Pedido {

    private Long id;

    private String codigoPedido;
    private LocalDateTime momentoDoPedido;
    private BigDecimal total;
    Map<Produto,Integer> produtos;
    StatusPedido statusPedido;

    public Pedido(Map<Produto, Integer> produtos){

        this.codigoPedido = UUID.randomUUID().toString();
        this.momentoDoPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        total = total(produtos);
        this.produtos = produtos;

    }

    public BigDecimal total(Map<Produto,Integer> listaDeProdutos){
        BigDecimal subtotal = BigDecimal.ZERO;
        for(Map.Entry<Produto,Integer> produto : listaDeProdutos.entrySet()){
            BigDecimal soma = produto.getKey().getPreco();
            soma = soma.multiply(new BigDecimal(produto.getValue()));

            subtotal = subtotal.add(soma);
        }
        return subtotal;
    }

    public String getCodigoPedido(){
        return codigoPedido;
    }
    public LocalDateTime getMomentoDoPedido(){
        return momentoDoPedido;
    }
    public StatusPedido getStatusPedido(){
        return statusPedido;
    }
    public Map<Produto,Integer> getProdutos(){
        return produtos;
    }
    public BigDecimal getTotal(){
        return total;
    }

    public void setStatusPedido(StatusPedido statusPedido){
        this.statusPedido = statusPedido;
    }

}

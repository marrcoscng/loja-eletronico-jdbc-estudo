package br.com.loja_geral.model;

import br.com.loja_geral.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Pedido {

    private Long id_pedido;
    private Long id_cliente;

    private String codigoPedido;
    private LocalDateTime momentoDoPedido;
    private StatusPedido statusPedido;
    private ArrayList<ItemPedido> itens;


    public Pedido(Long id_cliente, ArrayList<ItemPedido> lista){

        this.id_cliente = id_cliente;

        this.codigoPedido = UUID.randomUUID().toString();
        this.momentoDoPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        itens = lista;

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

    public BigDecimal getTotal(){
        BigDecimal soma = BigDecimal.ZERO;
        if(itens.size() > 0 ){
            for(ItemPedido item : itens){
                soma = soma.add(item.getSubtotal());
            }
        }
        return soma;
    }

    public void setStatusPedido(StatusPedido statusPedido){
        this.statusPedido = statusPedido;
    }

    public Long getId_pedido(){
        return id_pedido;
    }

    public Long getId_cliente(){
        return id_cliente;
    }

}

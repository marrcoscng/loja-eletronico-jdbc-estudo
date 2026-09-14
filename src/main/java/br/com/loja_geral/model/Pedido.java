package br.com.loja_geral.model;

import br.com.loja_geral.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private Long idPedido;
    private Long idCliente;

    private String codigoPedido;
    private LocalDateTime momentoDoPedido;
    private StatusPedido statusPedido;
    private List<ItemPedido> itens;


    public Pedido(Long idCliente, List<ItemPedido> lista){

        this.idCliente = idCliente;

        this.codigoPedido = UUID.randomUUID().toString();
        this.momentoDoPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        itens =(lista!=null)? lista: new ArrayList<>();

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

    public Long getIdPedido(){
        return idPedido;
    }

    public Long getIdCliente(){
        return idCliente;
    }

}

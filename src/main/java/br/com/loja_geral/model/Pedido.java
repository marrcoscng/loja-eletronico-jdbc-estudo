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

    private UUID codigoPedido;
    private LocalDateTime momentoDoPedido;
    private StatusPedido statusPedido;
    private List<ItemPedido> itens;


    // -> Construtor para negócio
    public Pedido(Long idCliente, List<ItemPedido> lista){

        this.idCliente = idCliente;

        this.codigoPedido = UUID.randomUUID();
        this.momentoDoPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        itens =(lista!=null)? lista: new ArrayList<>();

    }

    // -> Construtor para busca em Banco de Dados
    public Pedido(Long idCliente, UUID codigoPedido, LocalDateTime momentoDoPedido, StatusPedido statusPedido, List<ItemPedido> itens ){

        this.idCliente = idCliente;
        this.codigoPedido = codigoPedido;
        this.momentoDoPedido = momentoDoPedido;
        this.statusPedido = statusPedido;
        this.itens = itens;

    }

    public UUID getCodigoPedido(){
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

    public List<ItemPedido> getListaItens(){
        return new ArrayList<>(itens);
    }


    public void setIdCliente(Long idCliente){
        if(idCliente == null || idCliente<0){
            return;
        }
        this.idCliente = idCliente;
    }
}

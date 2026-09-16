package br.com.loja_geral.model.enums;

public enum StatusPedido {

    CANCELADO("Cancelado",-1),
    AGUARDANDO_CONFIRMACAO("Aguardando confirmação",1),
    CONFIRMADO("Pedido confirmado",2),
    EM_PREPARACAO("Pedido está sendo preparado para envio",3),
    ROTA_DE_ENTREGA("Pedido está em rota de entrega",4),
    PEDIDO_ENTREGUE("Pedido entregue",5);

    private final String status;
    private final int peso;

    StatusPedido(String status, int peso){
        this.status = status;
        this.peso = peso;
    };

    public int getPeso(){
        return peso;
    }
    public String getStatus(){
        return status;
    }

    public StatusPedido getProximo(){
        for(StatusPedido status : StatusPedido.values()){
            if(status.getPeso() == this.peso+1){
                return status;
            }
        }
        return null;
    }
}

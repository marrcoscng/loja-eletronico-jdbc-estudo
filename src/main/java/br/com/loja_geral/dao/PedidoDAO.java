package br.com.loja_geral.dao;

import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.model.enums.StatusPedido;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PedidoDAO <T extends Pedido>{

    Pedido buscarPedidoPorId(Long idPedido) throws SQLException;
    Map<UUID,Pedido> listaDePedidsoPorClienteId(Long idCliente) throws SQLException;
    Map<StatusPedido,Pedido> listaDePedidosPorStatus(StatusPedido statusPedido) throws SQLException;
    String obterCodigoDeRastreio(Long idPedido)  throws SQLException;
    void atualizarStatusPedido(Pedido pedido) throws SQLException;
    void cancelarPedido(Long idPedido) throws SQLException;
    void salvarPedido(T pedido) throws SQLException;


}

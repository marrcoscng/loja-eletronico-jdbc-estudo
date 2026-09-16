package br.com.loja_geral.dao;

import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.model.enums.StatusPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PedidoDAOJDBC implements PedidoDAO<Pedido>{

    private Connection conn;
    public PedidoDAOJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public Pedido buscarPedidoPorId(Long idPedido) {

        return null;
    }

    @Override
    public List<Pedido> listaDePedidsoPorClienteId(Long idCliente) {
        return List.of();
    }

    @Override
    public List<Pedido> listaDePedidosPorStatus(StatusPedido statusPedido) {
        return List.of();
    }

    @Override
    public String obterCodigoDeRastreio(Long idPedido) {
        return "";
    }

    @Override
    public void atualizarStatusPedido(Long idPedido, StatusPedido novoStatus) {

    }

    @Override
    public void marcarComoEntregue(Long idPedido) {

    }

    @Override
    public void cancelarPedido(Long idPedido) {

    }

    @Override
    public void salvarPedido(Pedido pedido){
        String enviarPedido = "INSERT INTO pedido(id_cliente, codigo_pedido, momento_do_pedido, status_pedido ) VALUES (?,?,?,?);";
            try (PreparedStatement preparedStatement = conn.prepareStatement(enviarPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                System.out.println(pedido.getStatusPedido().toString());
                preparedStatement.setLong(1, pedido.getIdCliente());
                preparedStatement.setString(2, pedido.getCodigoPedido().toString());
                preparedStatement.setObject(3, pedido.getMomentoDoPedido());
                preparedStatement.setString(4, pedido.getStatusPedido().name().toUpperCase());

                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        Long idPedido = resultSet.getLong(1);
                        try (PreparedStatement preparedStatement1 = conn.prepareStatement("INSERT INTO itens_pedido(id_pedido,id_produto,quantidade,preco) VALUES (?,?,?,?)")) {
                            for (int i = 0; i < pedido.getListaItens().size(); i++) {
                                preparedStatement1.setLong(1, idPedido);
                                preparedStatement1.setLong(2, pedido.getListaItens().get(i).getProduto().getId());
                                preparedStatement1.setInt(3, pedido.getListaItens().get(i).getQuantidade());
                                preparedStatement1.setBigDecimal(4, pedido.getListaItens().get(i).getProduto().getPreco());

                                preparedStatement1.executeUpdate();
                            }
                        }
                    }
                }
        }catch(SQLException e){
            System.err.println("Erro ao tentar salvar pedido! "+e.getMessage());
        }
    }
}

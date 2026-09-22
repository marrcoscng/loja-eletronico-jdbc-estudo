package br.com.loja_geral.dao;

import br.com.loja_geral.model.ItemPedido;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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
    public Map<UUID,Pedido> listaDePedidsoPorClienteId(Long idCliente) throws SQLException {

        Map<UUID,Pedido> pedidos = new LinkedHashMap<>();
        String listaDePedidosPorCliente =
                "SELECT pedido.id AS id_pedido, pedido.id_cliente, pedido.momento_do_pedido, pedido.codigo_pedido, pedido.status_pedido, produto.nome, produto.descricao, produto.path_file, produto.preco, itens_pedido.quantidade FROM pedido "+
                "INNER JOIN itens_pedido ON itens_pedido.id_pedido = pedido.id "+
                "INNER JOIN produto ON produto.id = itens_pedido.id_produto "+
                "WHERE pedido.id_cliente = ? "+
                "ORDER BY pedido.id ASC;";

        try(PreparedStatement preparedStatement = conn.prepareStatement(listaDePedidosPorCliente)){
            preparedStatement.setLong(1,idCliente);
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                while(resultSet.next()){

                    UUID codigo_pedido = resultSet.getObject("codigo_pedido",UUID.class);

                    if(!pedidos.containsKey(codigo_pedido)){
                        Long id_pedido = resultSet.getLong("id_pedido");
                        Long id_cliente = resultSet.getLong("id_cliente");
                        LocalDateTime momento_do_pedido = resultSet.getObject("momento_do_pedido",LocalDateTime.class);
                        StatusPedido statusPedido = StatusPedido.valueOf(resultSet.getString("status_pedido"));

                        Pedido pedido = new Pedido(id_pedido, id_cliente, codigo_pedido, momento_do_pedido, statusPedido, new ArrayList<>());
                        pedidos.put(codigo_pedido,pedido);

                    }

                    String nome = resultSet.getString("nome");
                    String descricao = resultSet.getString("descricao");
                    String path_file = resultSet.getString("path_file");
                    BigDecimal preco = resultSet.getObject("preco",BigDecimal.class);
                    Integer quantidade = resultSet.getInt("quantidade");

                    Produto produto = new Produto(nome,descricao,path_file,preco);
                    pedidos.get(codigo_pedido).getListaItens().add(new ItemPedido(produto,quantidade));

                }
            }
        }
        return pedidos;
    }

    @Override
    public Map<StatusPedido,Pedido> listaDePedidosPorStatus(StatusPedido statusPedido) {
        return new HashMap<>();
    }

    @Override
    public String obterCodigoDeRastreio(Long idPedido) {
        return "";
    }

    @Override
    public void atualizarStatusPedido(Pedido pedido) throws SQLException{

        String atualizarPedido = "UPDATE pedido SET status_pedido = ? WHERE id = ?;";
        try(PreparedStatement preparedStatement = conn.prepareStatement(atualizarPedido)){
            preparedStatement.setString(1, pedido.getStatusPedido().name());
            preparedStatement.setLong(2, pedido.getIdPedido());
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void cancelarPedido(Long idPedido)  {

    }

    @Override
    public void salvarPedido(Pedido pedido)  throws SQLException{
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
        }
    }
}

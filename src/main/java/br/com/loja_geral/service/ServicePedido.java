package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.PedidoDAO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ServicePedido {

    public void salvarPedido(Pedido pedido){

        try(Connection conn = DBConnection.getConnection()){
            try {
                conn.setAutoCommit(false);
                PedidoDAO pedidoDAO = DAOFactory.getPedidoDAO(conn);
                pedidoDAO.salvarPedido(pedido);
                conn.commit();
            }catch(SQLException e){
                conn.rollback();
            }
        }catch(SQLException e){
            throw new DBException("Erro em SQL ao iniciar banco de dados - "+e.getMessage());
        }
    }


}

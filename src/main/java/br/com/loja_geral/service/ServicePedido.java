package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.PedidoDAO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.StatusMaximoException;
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

    public void atualizarStatusPedido(Pedido pedido){
        if(pedido.getStatusPedido().getProximo() == null){
            throw new StatusMaximoException("Pedido já está em seu status máximo. ");
        }

        try(Connection conn = DBConnection.getConnection()){
            try{
                conn.setAutoCommit(false);
                PedidoDAO pedidoDAO = DAOFactory.getPedidoDAO(conn);
                pedidoDAO.atualizarStatusPedido(pedido);
                conn.commit();
            }catch(SQLException e){
                try {
                    conn.rollback();
                    throw new DBException("Erro ao atualizar status do pedido - "+e.getMessage());
                }catch(SQLException er){
                    throw new DBException("Erro ao tentar fazer rollback() - "+e.getMessage());
                }
            }

        }catch(SQLException e){
            throw new DBException("Erro ao conectar banco de dados - "+e.getMessage());
        }
    }


}

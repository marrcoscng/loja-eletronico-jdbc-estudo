package br.com.loja_geral.controller;

import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.PrecoInvalidoException;
import br.com.loja_geral.exception.ProdutoJaCadastradoException;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.service.ServiceProduto;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ProdutoController {

    public void salvarProduto(String nome, String descricao, String PATH_FILE, BigDecimal preco){
        try {
            Produto produto = new Produto(nome, descricao, PATH_FILE, preco);
            ServiceProduto serviceProduto = new ServiceProduto();
            serviceProduto.salvarProduto(produto);
        }catch(PrecoInvalidoException e){
            System.err.println("Error preco - "+e.getMessage());
        }catch(ProdutoJaCadastradoException e){
            System.out.println("Error er -"+e.getMessage());
        }catch(DBException e){
            System.err.println("Error intern database - "+e.getMessage());
        }
    }

}

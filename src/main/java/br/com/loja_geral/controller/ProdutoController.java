package br.com.loja_geral.controller;

import br.com.loja_geral.dto.ProdutoEstoqueDTO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.PrecoInvalidoException;
import br.com.loja_geral.exception.ProdutoJaCadastradoException;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.service.ServiceProduto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private final ServiceProduto serviceProduto;

    public ProdutoController(ServiceProduto serviceProduto){
        this.serviceProduto = serviceProduto;
    }

    public void salvarProduto(String nome, String descricao, String PATH_FILE, BigDecimal preco, Integer qtda){
        try {
            Produto produto = new Produto(nome, descricao, PATH_FILE, preco);
            serviceProduto.salvarProduto(produto,qtda);
        }catch(PrecoInvalidoException e){
            System.err.println("Error preco - "+e.getMessage());
        }catch(ProdutoJaCadastradoException e){
            System.out.println("Error er -"+e.getMessage());
        }catch(DBException e){
            System.err.println("Error intern database - "+e.getMessage());
        }
    }

    public List<ProdutoEstoqueDTO> buscarProdutos(){
        return serviceProduto.buscarProdutos();
    }

    public void deletarProdutoPorId(Long id){
        try {
            serviceProduto.deletarProdutoPorId(id);
        }catch(DBException e){
            System.err.println("Erro - "+e.getMessage());
        }
    }

}

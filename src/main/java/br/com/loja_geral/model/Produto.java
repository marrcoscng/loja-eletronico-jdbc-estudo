package br.com.loja_geral.model;

import br.com.loja_geral.exception.DadoInvalidoDoProdutoException;
import br.com.loja_geral.exception.PrecoInvalidoException;
import br.com.loja_geral.model.enums.Categoria;

import java.math.BigDecimal;

public class Produto  {

    private Long id;

    private String nome;
    private String descricao;
    private String PATH_FILE;
    private BigDecimal preco;
    private Categoria categoria;

    public Produto(String nome, String descricao, String PATH_FILE, BigDecimal preco){
        this.nome = nome;
        this.descricao = descricao;
        this.PATH_FILE  =PATH_FILE;
        this.preco = preco;

    }

    public String getNome(){
        return nome;
    }
    public Long getId(){
        return id;
    }

    public BigDecimal getPreco(){
        return preco;
    }

    public String getDescricao(){
        return descricao;
    }

    public String getPATH_FILE(){
        return PATH_FILE;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public String toString(){
        return String.format(
                "Nome: %s%n"+
                        "Descrição: %s%n"+
                        "Preço: %.2f%n"+
                        "Categoria: %s%n",nome,descricao,preco,categoria);
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setPreco(BigDecimal preco){
        if(preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new PrecoInvalidoException("Preço inválido");
        }
        this.preco = preco;
    }

    public void setDescricao(String descricao){
        if(descricao == null || descricao.isBlank()){
            throw new DadoInvalidoDoProdutoException("Descrição inválida");
        }
        this.descricao = descricao;
    }

    public void setPATH_FILE(String PATH_FILE){
        if(PATH_FILE == null){
            throw new DadoInvalidoDoProdutoException("Caminho de imagem inválido!");
        }
        this.PATH_FILE = PATH_FILE;
    }

    public void setCategoria(Categoria categoria){
        if(categoria == null){
            throw new DadoInvalidoDoProdutoException("Categoria inválida!");
        }
        this.categoria = categoria;
    }

    // melhorar futuramente ->
    public void setId(Long id){
        this.id = id;
    }


}

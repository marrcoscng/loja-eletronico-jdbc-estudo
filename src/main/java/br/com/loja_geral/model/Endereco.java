package br.com.loja_geral.model;

public class Endereco {

    private String rua;
    private String bairro;
    private String cidade;
    private String cep;
    private String numeroDaCasa;

    public Endereco(String rua, String bairro, String cidade, String cep, String numeroDaCasa){
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.numeroDaCasa = numeroDaCasa;
    }

    @Override
    public String toString(){
        return String.format(
                "Rua: %s%n" +
                "Bairro: %s%n"+
                "Cidade: %s%n"+
                "Número: %s%n",rua,bairro,cidade,numeroDaCasa);
    }

    public String getRua(){
        return rua;
    }
    public String getBairro(){
        return bairro;
    }


}

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

    public void setEndereco(Endereco endereco){
        rua = endereco.getRua();
        bairro = endereco.getBairro();
        cidade = endereco.getCidade();
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
    public String getCidade(){
        return cidade;
    }
    public String getNumeroDaCasa(){
        return numeroDaCasa;
    }
    public String getCep(){
        return cep;
    }


}

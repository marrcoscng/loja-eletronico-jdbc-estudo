package br.com.loja_geral.util;

import br.com.loja_geral.exception.DocumentoInvalidoException;

public class ValidadorCpf {

    public static void validadorCpf(String cpf){

        if(cpf == null ){
            throw new DocumentoInvalidoException("Documento inválido, fora do padrão XXX.XXX.XXX-XX");
        }

        String novCpf = cpf.replaceAll("\\D", "");

        if(novCpf.length()!=11 ){
            throw new DocumentoInvalidoException("Documento inválido, fora do padrão XXX.XXX.XXX-XX");
        }

        if(
                novCpf.equals("00000000000") || novCpf.equals("11111111111") ||
                novCpf.equals("22222222222") || novCpf.equals("33333333333") ||
                novCpf.equals("44444444444") || novCpf.equals("55555555555") ||
                novCpf.equals("66666666666") || novCpf.equals("77777777777") ||
                novCpf.equals("88888888888") || novCpf.equals("99999999999")
        ){
            throw new DocumentoInvalidoException("Documento inválido!");
        }
        verificacaoDosDigitos(novCpf);

    }

    private static void verificacaoDosDigitos(String CPF){

        int soma = 0;
        int digitoEsperado1 = 0;
        int digitoEsperado2 = 0;

        for(int indice = 0, peso = 10; indice<9 ; indice++, peso--){
            int numero = Integer.parseInt(CPF.substring(indice,indice+1));
            soma+=numero*peso;
        }
        digitoEsperado1 = (soma*10)%11;
        soma = 0;
        if(digitoEsperado1 == 10 && CPF.charAt(9)!='0'){
            throw new DocumentoInvalidoException("Documento inválido!");
        }
        if(digitoEsperado1 != 10 && digitoEsperado1 != Integer.parseInt(CPF.substring(9,10))){
            throw new DocumentoInvalidoException("Documento inválido!");
        }


        for(int indice = 0, peso = 11; indice<10 ; indice++, peso--){
            int numero = Integer.parseInt(CPF.substring(indice,indice+1));
            soma+=numero*peso;
        }
        digitoEsperado2 = (soma*10)%11;
        if(digitoEsperado2 == 10 && CPF.charAt(10)!='0'){

            throw new DocumentoInvalidoException("Documento inválido!");
        }
        if(digitoEsperado2 != 10 && digitoEsperado2 != Integer.parseInt(CPF.substring(10))){
            throw new DocumentoInvalidoException("Documento inválido!");
        }

    }
}

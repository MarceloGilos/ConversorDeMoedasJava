package com.bapptech.Componentes;

import java.util.List;

public class Cotacao {

    private String bid;
    private String ask;
    
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getAsk() {
        return ask;
    }
    public void setAsk(String ask) {
        this.ask = ask;
    }
    /*Conversão 1 ex: USD - BR -> Onde tenho a apenas a cotação de dolar para real porem quero
    saber quantos dolares são 100R$. entao utilizo o o valor em real e divido pelo valor da cotação
    100/cotação.*/ 
    public StringBuffer conversao1(Double valor){
        StringBuffer string = new StringBuffer(String.valueOf(valor / Double.parseDouble(this.getBid())));

        return string;
    }
    /* Conversao 2 ex:USD-BR -> Onde tenho a cotação do USD PARA BR e quero saber quanto valo 100$
     * em Real, então como tenho quie 1 $ vale 4.95R$ então tenho que 100$ em real = 100*cotação
     */
        public StringBuffer conversao2(Double valor){
        StringBuffer string = new StringBuffer(String.valueOf(valor * Double.parseDouble(this.getBid())));

        return string;
    }
//Metodo usado para retornar se a combinação de conversão exite na tabela XML da API.
/*Recebe como parametro A lista com as classes que contem os atributos tag e nome , onde tag é
 * a moeda original e tag a destino. E recebe como parametro tambem outro Objeto da classe Moedas
 * Que vai ter as tags de conversao que quero saber se tem na lsita.Esse metodo me retorna
 * false se não tiver e true se estiver na lista
 * ex:USD-BRL->RETORNA TRUE , POIS A API RETORNA ESSA COMBINAÇÃO.
*/
    public static boolean containsValidation(List<Moedas> tag, Moedas moeda){
        for(Moedas tag1: tag){
            if(moeda.getNome().equals(tag1.getNome()) && moeda.getTag().equals(tag1.getTag())){
                return true;
            }
        }
        return false;
    }
}

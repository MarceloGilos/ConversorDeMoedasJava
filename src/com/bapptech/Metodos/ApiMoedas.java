package com.bapptech.Metodos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.bapptech.Componentes.Cotacao;
import com.bapptech.Componentes.Moedas;
import com.google.gson.Gson;

public class ApiMoedas {
    // METODO PARA REQUISITAR DADOS DA API DE COTAÇÃO DE MOEDAS
    static public Cotacao apiMoedasRequest(String moeda1, String moeda2) throws IOException, InterruptedException {
        // CRIO UM OBJETO CLIENT UTILIZANDO HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // CRIO O OBJETO REQUISITOR UTILIZANDO O HttpRquest e o metodo NewBuilder().URI
        // que vai me passa a API e construir um JSON
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://economia.awesomeapi.com.br/last/" + moeda1 + "-" + moeda2)).build();
        // Cripo um HttpResponse que vai armazenar os dados enviados pela API em strings
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // e depois crio um stringbuffer que vai armazenar o corpo dessa resposta
        StringBuffer responseString = new StringBuffer(response.body());
        // retorna já convertido para
        return converterJson(responseString);
    }

    // Metodo usado para carregar em OBJETOS os dados passado em JSON do metodo a
    // cima
    static public Cotacao converterJson(StringBuffer response) {
        // Crio o objeto JSON da biblioteca Gson.
        Gson json = new Gson();
        /*
         * Aqui como minha API passa de forma diferente os dados que preciso, isso é
         * apenas uma correção
         * onde configuro a string que eu recebi em JSON para o padrão que eu preciso.
         */
        response = ConvertString.deletarString(response);
        // Crio o Objeto utilizando o FromJson que recebe o JSON criado e a classe to
        // tipo do Objeto
        Cotacao cotacao = json.fromJson(response.toString(), Cotacao.class);
        // Retornando assim o Objeto ja carregado com os dados que eu preciso
        return cotacao;

    }

    /*
     * Método que irá validar e converter os valores, recebe como parametro 2
     * strings, que são as tags
     * da moeda destino e origem e o valor que vai ser convertido.
     */
    static public StringBuffer correcaoConversao(String moeda1, String moeda2, Double valor)
            throws IOException, InterruptedException {
        /* */
        @SuppressWarnings("unchecked")
        /*
         * tags é uma Lista que é carregada chamando o método leitorXml que pede como
         * parametro
         * o um Fileimput, esse Fileimput é gerado pelo metodo leitorPasta que esta
         * tambem
         * na classe Xstream.java ele recebe como parametro uma string com o diretorio
         * do arquivo xml
         * e me retorna o Fileinput que necessito.
         */
        List<Moedas> tags = new ArrayList<Moedas>(
                (List<Moedas>) Xstream.leitorXml(Xstream.leitorPasta("CombinacoesBdd.xml"), Moedas.class));
        /*
         * Reutilizando a classe moeda crio um objeto mas ao inves de ser o nome e a
         * tag, eu armazeno
         * a tag das duas moedas, a origem e a destino ex: BR - USD
         */
        Moedas procura = new Moedas(moeda1, moeda2);
        /*
         * Utilziando o metodo containsValidation da classe Cotacao comparo as duas
         * moedas
         * com um outro arquivo XML que me passa todas as combinações de moedas da API
         */
        // Esse IF vai comparar primeiro se existe uam cotção direta da moeda de origem
        // para destino
        if (Cotacao.containsValidation(tags, procura)) {
            /*
             * Armazeno em uma StringBuff o resultado da conversao utilizando os dados da
             * API
             */
            StringBuffer valor1 = new StringBuffer(apiMoedasRequest(moeda1, moeda2).conversao2(valor));
            return valor1;
        } else {
            // Objeto criado para o sgundo IF para ver se a cotação inversa existe
            Moedas procuraElse = new Moedas(moeda2, moeda1);
            if (Cotacao.containsValidation(tags, procuraElse)) {
                /*
                 * Armazeno em uma StringBuff o resultado da conversao utilizando os dados da
                 * API
                 */
                StringBuffer valor1 = new StringBuffer(apiMoedasRequest(moeda2, moeda1).conversao1(valor));
                return valor1;
            } else {
                /*
                 * Aqui temos que atualizar para otimizar, porem faz oque é necessario
                 * Como a API não tem todas as combinações, porem tenho uma moeda onde ela tem
                 * quase todas as combinações que é o USD(dolar) porem preciso validar para
                 * saber
                 * se a combinação é de dolar para a moeda ou da moeda para o dolar.
                 * exitindo 4 combinações possiveis
                 * 1-tanto a moeda origem quanto a destino só existem cotação do dolar para elas
                 * entao preciso converter a primeira para dolar e aseguda de dolar para
                 * destino.
                 * 2-igual a situação a cima porem as duas moedas tem cotação delas para dolar
                 * então preciso converter a primeira para dolar e a segunda de dolar para
                 * destino
                 * 3-a primeira moeda tem cotação de dolar e a segunda dela para o dolar, então
                 * converto a primeira para dolar e a segunda de dolar para destino
                 * 4- O ultimo caso, é o inverso da de cima, a primeira moeda tem contação de
                 * dolar para ela e a segunda dela para cota.
                 * TODOS OS IF A BAIXO SÃO PARA ISSO E VAO RETORNAR O VALOR DA CONVERSÃO DIRETO
                 * PUXANDO PELA API OS DADOS NECESSARIOS.
                 */
                Moedas procuraUsd1 = new Moedas("USD", moeda1);
                Moedas procuraUsd2 = new Moedas("USD", moeda2);
                Moedas procuraUsd3 = new Moedas(moeda1, "USD");
                Moedas procuraUsd4 = new Moedas(moeda2, "USD");
                if (Cotacao.containsValidation(tags, procuraUsd1) && Cotacao.containsValidation(tags, procuraUsd2)) {
                    StringBuffer valor1 = new StringBuffer(apiMoedasRequest(moeda1, "USD").conversao2(valor));
                    StringBuffer valor2 = new StringBuffer(
                            apiMoedasRequest(moeda2, "USD").conversao1(Double.parseDouble(valor1.toString())));
                    return valor2;
                } else if (Cotacao.containsValidation(tags, procuraUsd3)
                        && Cotacao.containsValidation(tags, procuraUsd4)) {

                    StringBuffer valor1 = new StringBuffer(apiMoedasRequest("USD", moeda1).conversao1(valor));
                    StringBuffer valor2 = new StringBuffer(
                            apiMoedasRequest("USD", moeda2).conversao2(Double.parseDouble(valor1.toString())));
                    return valor2;
                } else if (Cotacao.containsValidation(tags, procuraUsd3)
                        && Cotacao.containsValidation(tags, procuraUsd2)) {

                    StringBuffer valor1 = new StringBuffer(apiMoedasRequest("USD", moeda1).conversao1(valor));
                    StringBuffer valor2 = new StringBuffer(
                            apiMoedasRequest(moeda2, "USD").conversao2(Double.parseDouble(valor1.toString())));
                    return valor2;
                } else {

                    System.out.println(moeda1 + moeda2);
                    StringBuffer valor1 = new StringBuffer(apiMoedasRequest(moeda1, "USD").conversao2(valor));
                    StringBuffer valor2 = new StringBuffer(
                            apiMoedasRequest("USD", moeda2).conversao2(Double.parseDouble(valor1.toString())));
                    return valor2;
                }
            }
        }

    }

}

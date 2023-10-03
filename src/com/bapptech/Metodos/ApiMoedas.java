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
import com.bapptech.Conversoes.Conversao;
import com.bapptech.Conversoes.ConversaoDireta;
import com.bapptech.Conversoes.ConversaoDolarMoedaDolarMoeda;
import com.bapptech.Conversoes.ConversaoDolarMoedaMoedaDolar;
import com.bapptech.Conversoes.ConversaoInvertida;
import com.bapptech.Conversoes.ConversaoMoedaDolarDolarMoeda;
import com.bapptech.Conversoes.ConversaoMoedaDolarMoedaDolar;
import com.bapptech.Conversoes.SemConversao;
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

        Conversao conversao = new ConversaoDireta(new ConversaoInvertida(
                new ConversaoMoedaDolarMoedaDolar(new ConversaoDolarMoedaDolarMoeda(new ConversaoDolarMoedaMoedaDolar(
                        new ConversaoMoedaDolarDolarMoeda(new SemConversao(null)))))));
                            return conversao.converter(moeda1, moeda2, tags, valor);
    }

}

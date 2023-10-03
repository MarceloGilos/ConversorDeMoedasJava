package com.bapptech.Conversoes;

import java.io.IOException;
import java.util.List;

import com.bapptech.Componentes.Cotacao;
import com.bapptech.Componentes.Moedas;
import com.bapptech.Metodos.ApiMoedas;

public class ConversaoMoedaDolarDolarMoeda extends Conversao {

    public ConversaoMoedaDolarDolarMoeda(Conversao proximo) {
        super(proximo);

    }

    @Override
    public StringBuffer converter(String moedas1, String moedas2, List<Moedas> listas, Double valor)
            throws IOException, InterruptedException {
        if (Cotacao.containsValidation(listas, new Moedas(moedas1, "USD"))
                && Cotacao.containsValidation(listas, new Moedas("USD", moedas2))) {
            StringBuffer valor1 = new StringBuffer(ApiMoedas.apiMoedasRequest(moedas1, "USD").conversao2(valor));
            StringBuffer valor2 = new StringBuffer(
                    ApiMoedas.apiMoedasRequest("USD", moedas2).conversao1(Double.parseDouble(valor1.toString())));
            return valor2;
        }
        return proximo.converter(moedas1, moedas2, listas, valor);

    }
}

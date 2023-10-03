package com.bapptech.Conversoes;

import java.io.IOException;
import java.util.List;

import com.bapptech.Componentes.Cotacao;
import com.bapptech.Componentes.Moedas;
import com.bapptech.Metodos.ApiMoedas;

public class ConversaoMoedaDolarMoedaDolar extends Conversao {

    public ConversaoMoedaDolarMoedaDolar(Conversao proximo) {
        super(proximo);

    }

    @Override
    public StringBuffer converter(String moedas1, String moedas2, List<Moedas> listas, Double valor)
            throws IOException, InterruptedException {
        if (Cotacao.containsValidation(listas, new Moedas(moedas1, "USD"))
                && Cotacao.containsValidation(listas, new Moedas(moedas2, "USD"))) {
            StringBuffer valor1 = new StringBuffer(ApiMoedas.apiMoedasRequest("USD", moedas1).conversao1(valor));
            StringBuffer valor2 = new StringBuffer(
                    ApiMoedas.apiMoedasRequest("USD", moedas2).conversao2(Double.parseDouble(valor1.toString())));
            return valor2;
        }
        return proximo.converter(moedas1, moedas2, listas, valor);

    }

}

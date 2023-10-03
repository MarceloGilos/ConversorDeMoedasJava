package com.bapptech.Conversoes;

import java.io.IOException;
import java.util.List;

import com.bapptech.Componentes.Cotacao;
import com.bapptech.Componentes.Moedas;
import com.bapptech.Metodos.ApiMoedas;

public class ConversaoDireta extends Conversao {
    public ConversaoDireta(Conversao proximo) {
        super(proximo);
    }

    @Override
    public StringBuffer converter(String moedas1, String moedas2, List<Moedas> listas, Double valor) throws IOException, InterruptedException {
        if (Cotacao.containsValidation(listas, new Moedas(moedas1, moedas2))) {
            /*
             * Armazeno em uma StringBuff o resultado da conversao utilizando os dados da
             * API
             */
            StringBuffer valor1 = new StringBuffer(ApiMoedas.apiMoedasRequest(moedas1, moedas2).conversao2(valor));
            return valor1;
        }
        return proximo.converter(moedas1, moedas2, listas, valor);
    }
}

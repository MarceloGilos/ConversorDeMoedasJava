package com.bapptech.Conversoes;

import java.io.IOException;
import java.util.List;

import com.bapptech.Componentes.Moedas;

public class SemConversao extends Conversao{

    public SemConversao(Conversao proximo) {
        super(null);
    }

    @Override
    public StringBuffer converter(String moedas1, String moedas2, List<Moedas> listas, Double valor)
            throws IOException, InterruptedException {
                return null;
    }
    
}

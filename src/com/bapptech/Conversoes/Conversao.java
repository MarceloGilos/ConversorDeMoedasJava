package com.bapptech.Conversoes;

import java.io.IOException;
import java.util.List;

import com.bapptech.Componentes.Moedas;

public abstract class Conversao {
    protected Conversao proximo;

    public Conversao (Conversao proximo){
        this.proximo = proximo;
    }

    public abstract StringBuffer converter(String moedas1, String moedas2, List<Moedas> listas, Double valor) throws IOException, InterruptedException;
}

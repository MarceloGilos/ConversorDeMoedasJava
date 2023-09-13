package com.bapptech.Componentes;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Moeda")
public class Moedas {
    private String tag;
    private String nome;

    public Moedas(String tag, String nome) {
        this.tag = tag;
        this.nome = nome;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome() + getTag();

    }

}

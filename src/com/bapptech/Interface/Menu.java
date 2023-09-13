package com.bapptech.Interface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bapptech.Componentes.Moedas;
import com.bapptech.Metodos.ApiMoedas;
import com.bapptech.Metodos.Xstream;

public class Menu {
        static public void menuEscolha() throws IOException, InterruptedException {
                // Uso Suppress pq garanto que o cast que estou fazendo vai ser cumprido
                @SuppressWarnings("unchecked")
                /*
                 * moedas1 é uma Lista que é carregada chamando o método leitorXml que pede como
                 * parametro
                 * o um Fileimput, esse Fileimput é gerado pelo metodo leitorPasta que esta
                 * tambem
                 * na classe Xstream.java ele recebe como parametro uma string com o diretorio
                 * do arquivo xml
                 * e me retorna o Fileinput que necessito.
                 */
                List<Moedas> moedas1 = new ArrayList<Moedas>(
                                (List<Moedas>) Xstream.leitorXml(Xstream.leitorPasta("MoedasBdd.xml"), Moedas.class));
                Scanner scanner = new Scanner(System.in);
                // Vetor criado para armazenar as escolhas do usuario
                int escolha[] = new int[2];
                // Double que armazena o valor que o usario que converter
                double valor;
                /*
                 * Laço for para criar o menu que mostra todas as moedas que da para escolher,
                 * pegando esses
                 * dados da Lista gerada a cima utilizando o medo indexOF() para mostrar a
                 * posição que o Objeto
                 * está e getNome() para pegar o dados do atributo Nome de cada Objeto da lista
                 */
                for (Moedas moedas : moedas1) {
                        System.out.println(moedas1.indexOf(moedas) + 1 + "-" + moedas.getNome());
                }
                System.out.println("""
                                Escolha qual moeda você que converter:
                                """);
                escolha[0] = scanner.nextInt() - 1;
                System.out.println("""
                                Escolha para qual moeda você que converter:
                                """);
                escolha[1] = scanner.nextInt() - 1;
                System.out.println("Qual o valor em " + moedas1.get(escolha[0]).getTag());
                valor = scanner.nextDouble();
                scanner.close();
                /*
                 * Após requisitar a cima o valor qual a moeda de origem e a moeda que será
                 * convertida
                 * utilizo o metodo da Classe ApiMoedas correcaoConversao() que recebe como
                 * parametro
                 * 1 string com a tag da moeda de origem, 1 string com a tag da moeda destino e
                 * o valor
                 * a ser convertido, e ele vai fazer a validação e converter e retornar o
                 * resultado
                 */
                System.out.println(valor+" "+moedas1.get(escolha[0]).getTag() + " VALE:" + ApiMoedas.correcaoConversao(moedas1.get(escolha[0]).getTag(),
                                moedas1.get(escolha[1]).getTag(), valor)+" "+moedas1.get(escolha[1]).getTag());

        }
}

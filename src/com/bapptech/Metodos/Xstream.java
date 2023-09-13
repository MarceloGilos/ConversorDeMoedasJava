package com.bapptech.Metodos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Xstream {
    /*Método para ler arquivo XML-> recebe como parametro o caminho para o arquivo xml
     * e a classe do objeto que vai ser carregado*/
    static public List<?> leitorXml(FileInputStream fileInputStream,Class<?> objeto) throws FileNotFoundException {
        //Instacio um Xstream da biblioteca Xstream, que recebe com parametro um DomDriver()
        XStream xstream = new XStream(new DomDriver());
        /*"alias" usado para o xstream intrepetar como a lista vai carregar o Objeto
        no caso a lista é dividade com a tag "Dados" com 2 atributos o nome e a tag*/
        xstream.alias("Dados", objeto);
        //Para ignorar elementos desconhecidos
        xstream.ignoreUnknownElements();
        //Para liberar acesso
        xstream.allowTypeHierarchy(objeto);
        /*Crio a lista de objetos do tipo que vai ser carregada pelo xstream que revebe como parametro
         o caminho que foi passado como parametro no metodo */
        List<?> moedas = (List<?>) xstream.fromXML(fileInputStream);
        //retorno a lista
        return moedas;

    }
/*Metodo criado para transformar o caminho String da pasta xml em FileinputStream */
    static public FileInputStream leitorPasta(String diretorio) throws FileNotFoundException{
        FileInputStream inputStream = new FileInputStream(
                new File(diretorio));
                return inputStream;
    }

}

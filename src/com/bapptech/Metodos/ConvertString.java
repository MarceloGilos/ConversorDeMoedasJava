package com.bapptech.Metodos;

public class ConvertString {

    public static StringBuffer deletarString(StringBuffer string){
     string.delete(0, string.indexOf("{", string.indexOf("\"")));
     string.deleteCharAt(string.lastIndexOf("}"));

     return string;
    }
}

package eda.solutions;

import eda.ds.HashTable;

public class TestTabla {
    public static void main(String[] args) {
        HashTable<String, Integer> diccionario = new HashTable<>();

        diccionario.put("uno", 1);
        diccionario.put("dos", 2);
        diccionario.put("tres", 3);

        System.out.println("Valor de 'dos': " + diccionario.get("dos"));
        System.out.println("Contiene 'cuatro'?: " + diccionario.contains("cuatro"));

        diccionario.put("dos", 22);
        System.out.println("Nuevo valor de 'dos': " + diccionario.get("dos"));

        diccionario.remove("tres");
        System.out.println("Tamaño después de borrar 'tres': " + diccionario.size());

        System.out.println("\nDiccionario completo:");
        System.out.println(diccionario);
    }
}
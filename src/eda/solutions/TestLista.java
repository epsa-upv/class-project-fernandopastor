package eda.solutions;

import eda.adt.List;
import eda.ds.ListImpl;
import eda.exceptions.WrongIndexException;

public class TestLista {
    public static void main(String[] args) {
        try {
            ListImpl<Integer> lista= new ListImpl<>();
            lista.insert(0,10);
            lista.insert(1, 20);
            lista.insert(2, 30);
            lista.insert(1, 15);

            System.out.println("Tamaño: " + lista.size());
            System.out.println("Elemento en pos 1: " + lista.get(1));
            System.out.println("Posición del 30: " + lista.search(30));

            lista.delete(1);
            System.out.println("Tamaño después de borrar: " + lista.size());

            System.out.print("Lista: ");
            for (int num : lista) {
                System.out.print(num + " ");
            }
        }
        catch (WrongIndexException e) {
            e.printStackTrace();
        }
    }
}
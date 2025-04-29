package eda.solutions;

import eda.ds.BST;

public class TestABB {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();

        bst.add(40);
        bst.add(20);
        bst.add(60);
        bst.add(10);
        bst.add(30);
        bst.add(50);
        bst.add(70);

        System.out.println("Inorden:");
        for (int x : bst) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Buscar 30: " + (bst.search(30) != null ? "Encontrado" : "No encontrado"));
        System.out.println("Buscar 90: " + (bst.search(90) != null ? "Encontrado" : "No encontrado"));

        System.out.println("Mínimo: " + bst.min());
        System.out.println("Máximo: " + bst.max());

        System.out.println("Eliminando 20 y 40...");
        bst.delete(20);
        bst.delete(40);

        System.out.println("Inorden después de borrar:");
        for (int x : bst) {
            System.out.print(x + " ");
        }
    }
}


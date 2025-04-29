package eda.solutions;

import eda.ds.AVL;

class TestAVL {
    public static void main(String[] args) {
        AVL<Integer> avl = new AVL<>();

        System.out.println("== Insertando elementos ==");
        int[] valores = {30, 20, 40, 10, 25, 35, 50, 5, 15, 45, 60};
        for (int val : valores) {
            avl.add(val);
            System.out.println("Insertado: " + val);
        }

        System.out.println("\n== Recorrido postorden ==");
        for (Integer val : avl) {
            System.out.print(val + " ");
        }
        System.out.println();

        System.out.println("\n== Buscando valores ==");
        System.out.println("Buscar 25: " + (avl.search(25) != null));
        System.out.println("Buscar 100: " + (avl.search(100) != null));

        System.out.println("\n== Mínimo y máximo ==");
        System.out.println("Min: " + avl.min());
        System.out.println("Max: " + avl.max());

        System.out.println("\n== Eliminando algunos valores ==");
        int[] aEliminar = {30, 20, 40};
        for (int val : aEliminar) {
            avl.delete(val);
            System.out.println("Eliminado: " + val);
        }

        System.out.println("\n== Recorrido postorden tras eliminar ==");
        for (Integer val : avl) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}

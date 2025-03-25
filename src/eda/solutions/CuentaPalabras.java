package eda.solutions;

import eda.ds.HashTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CuentaPalabras {
    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "texto.txt";
        HashTable<String, Integer> contador = new HashTable<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                String palabra = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
                contador.put(palabra, contador.get(palabra) == null ? 1 : contador.get(palabra) + 1);
            }

            for (String palabra : contador) {
                System.out.println(palabra + ": " + contador.get(palabra));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + fileName);
        }
    }
}

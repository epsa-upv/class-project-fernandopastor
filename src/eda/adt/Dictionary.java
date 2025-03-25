package eda.adt;

import java.util.Iterator;

public interface Dictionary<K, V> extends Iterable<K> {
    V put(K key, V value);   // Inserta un elemento
    V get(K key);            // Recupera un elemento por clave
    V remove(K key);         // Elimina un elemento por clave
    boolean contains(K key); // Comprueba si existe una clave
    int size();              // Número de elementos
    boolean isEmpty();       // Comprueba si está vacío
    void clear();            // Elimina todos los elementos
    Iterator<K> iterator();  // Permite recorrer todas las claves
}
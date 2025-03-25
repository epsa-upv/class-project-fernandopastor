package eda.adt;

import eda.exceptions.WrongIndexException;

import java.util.Iterator;

public interface List<E> extends Iterable<E> {
    void insert(int pos, E data) throws WrongIndexException, WrongIndexException;
    void delete(int pos) throws WrongIndexException ;
    E get(int pos) throws WrongIndexException;
    int search(E data) ;
    Iterator <E> iterator();
    int size();

    void add(E kvTableEntry);

    void clear();
}

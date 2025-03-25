package eda.ds;

import eda.adt.List;
import eda.exceptions.WrongIndexException;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ListImpl<E> implements List<E> {
    private static class Node<E>{
        E data;
        Node<E> next;

        Node(E data, Node<E> next){
            this.data=data;
            this.next=next;
        }
    }
    private Node<E> head;
    private int size;

    public ListImpl() {
        head=null;
        size=0;
    }

    @Override
    public void insert(int pos, E data) throws WrongIndexException {
        if (pos<0 || pos>size) throw new WrongIndexException("Posición no válida");
        if(pos==0) {
            head=new Node<>(data,head);
        }
        else {
            Node<E> prev=getNode(pos-1);
            prev.next= new Node<>(data, prev.next);
        }
        size ++;
    }

    @Override
    public void delete(int pos) throws WrongIndexException {
        if (pos<0 || pos>size) throw new WrongIndexException("Posición no válida");
        if(pos==0) {
            head=head.next;
        }
        else {
            Node<E> prev=getNode(pos-1);
            prev.next=prev.next.next;
        }
    }

    @Override
    public E get(int pos) throws WrongIndexException{
        if (pos<0 || pos>size) throw new WrongIndexException("Posición no válida");
        return getNode(pos).data;
    }

    @Override
    public int search(E data) {
        Node<E> current=head;
        int index=0;
        while(current !=null) {
            if(current.data.equals(data)) return index;
            current=current.next;
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E kvTableEntry) {

    }

    @Override
    public void clear() {

    }

    private Node<E> getNode(int pos){
        Node<E> current=head;
        for(int i=0;i<pos;i++) {
            current=current.next;
        }
        return current;
    }

    @Override
    public Iterator<E> iterator() {
        return new CIterator();
    }

    private class CIterator implements Iterator<E>{
        private Node<E> current=head;

        @Override
        public boolean hasNext() {
            return current !=null;
        }

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            E data = current.data;
            current=current.next;
            return data;
        }
    }
}
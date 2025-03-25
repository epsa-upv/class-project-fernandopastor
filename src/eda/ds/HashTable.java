package eda.ds;

import eda.adt.Dictionary;
import eda.adt.List;
import eda.exceptions.WrongIndexException;

import java.util.Iterator;

public class HashTable<K, V> implements Dictionary<K, V> {
    private static class TableEntry<K, V> {
        K key;
        V value;

        TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<TableEntry<K, V>>[] table;
    private int size;
    private static final int Default_Capacity = 16;
    private static final double Load_Frequency = 0.75;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        int cap = (capacity > 0) ? capacity : Default_Capacity;
        table = new List[cap];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ListImpl<>();
        }
        size = 0;
    }

    public HashTable() {
        this(Default_Capacity);
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        List<TableEntry<K, V>> bucket = table[index];
        for (TableEntry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        try {
            bucket.insert(bucket.size(), new TableEntry<>(key, value));
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        size++;
        if ((double) size / table.length > Load_Frequency) {
            rehash();
        }
        return null;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        for (TableEntry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        List<TableEntry<K, V>> bucket = table[index];
        int pos = 0;
        for (TableEntry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                try {
                    bucket.delete(pos);
                } catch (WrongIndexException e) {
                    e.printStackTrace();
                }
                size--;
                return entry.value;
            }
            pos++;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (List<TableEntry<K, V>> bucket : table) {
            while (bucket.size() > 0) {
                try {
                    bucket.delete(0);
                } catch (WrongIndexException e) {
                    e.printStackTrace();
                }
            }
        }
        size = 0;
    }

    @Override
    public Iterator<K> iterator() {
        return new CIterator();
    }

    private class CIterator implements Iterator<K> {
        private int bucketIndex = 0;
        private Iterator<TableEntry<K, V>> bucketIterator = table[0].iterator();

        @Override
        public boolean hasNext() {
            while (bucketIndex < table.length) {
                if (bucketIterator.hasNext()) {
                    return true;
                }
                bucketIndex++;
                if (bucketIndex < table.length) {
                    bucketIterator = table[bucketIndex].iterator();
                }
            }
            return false;
        }

        @Override
        public K next() {
            return bucketIterator.next().key;
        }
    }

    private void rehash() {
        List<TableEntry<K, V>>[] oldTable = table;
        table = new List[oldTable.length * 2];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ListImpl<>();
        }
        size = 0;
        for (List<TableEntry<K, V>> bucket : oldTable) {
            for (TableEntry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        for (List<TableEntry<K, V>> bucket : table) {
            for (TableEntry<K, V> entry : bucket) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" }");
        return sb.toString();
    }
}
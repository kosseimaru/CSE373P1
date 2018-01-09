package datastructures.concrete.dictionaries;

import java.util.Dictionary;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V> pairs;
    
    // You're encouraged to add extra fields (and helper methods) though!
    private int size;
    private int currentIndex;
    private Pair<K, V>[] array;
    
    public ArrayDictionary() {
        this.pairs = null;
        this.array = this.makeArrayOfPairs(5);
        this.size = 0;
        this.currentIndex = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        return this.array[currentIndex].value; //? how do you know current index respond to the key you want
    }

    @Override
    public void put(K key, V value) {
        if (this.size == this.array.length) { // resizing
            Pair<K, V>[] newArray = this.makeArrayOfPairs(this.size * 2);
            for (int i = 0; i <= this.size; i++) { //dengxian: changed to <=
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        } else if (this.array[this.size] == null && !containsKey(key)) { // putting
//            dengxian: added not contains, changes made below
//            this.pairs.key = key;
//            this.pairs.value = value;
            this.pairs = new Pair<K,V>(key,value);
            this.array[this.size] = pairs;
            this.size++;
        } else { // replacing  
//          dengxian: fist find where is the existing key, then replace value
            Pair<K,V> newPair = new Pair<K,V>(key,value);   
            int curr = 0;
            while(this.array[curr].key != newPair.key) {
                curr++;
            }
            this.array[curr].value = value;
//            this.pairs.key = key;
//            this.pairs.value = value;
//            this.array[this.size] = pairs;
        }
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        this.pairs = this.array[this.currentIndex];
        for (int i = this.currentIndex + 1; i < this.size; i++) {
            this.array[i - 1] = this.array[i];
        }
        this.size--;
        this.array[this.size] = null;
        return this.pairs.value;
    }

    @Override
    public boolean containsKey(K key) {
        boolean containing = false;
        for (int i = 0; i < this.size; i++) { 
            if (this.array[i].key == key) {
                containing = true; //dengxian: changed to true
                currentIndex = i; //dengxian: why added this line?
            }
        }
        return containing;
    }

    @Override
    public int size() {
        return this.size;
    }
    
//  dengxian: toString method added, easier to test the code
    public String toString() {
        String result = "[";
        if (size > 0) {
           result += this.array[0];
           for (int i = 1; i < size; i++) {
              result += ", " + this.array[i];
           }
        }
        result += "]";
        return result;
     }
    
    
    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
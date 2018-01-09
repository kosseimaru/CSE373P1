package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.NotYetImplementedException;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see
 * the source code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    private Node<T> goTo(int index) {
        Node<T> current = this.front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    
    public String toString() {
        if (front == null) {
            return "[]";
        } else {
            String result = "[" + front.data;
            Node<T> current = front.next;
            while (current != null) {
                result += ", " + current.data;
                current = current.next;
            }
            result += "]";
            return result;
        }
    }
    
    @Override
    // add at the end, item could be string/int...
    public void add(T item) {
        if (this.front == null) {
            this.front = new Node<T>(item); 
            this.back = this.front;
        } else { 
            Node<T> curr = back;
            curr.next = new Node<T>(back,item,null);
            this.back = curr.next;    
        }
        this.size++;
    }

    @Override
    // remove at the end
    public T remove() {
        if (this.front == null) { //throw exception            
            throw new EmptyContainerException();
        }
        if (this.size == 1) { // only one, remove, become empty
            Node<T> curr = this.front;
            T currData = curr.data;
            this.front = null;
            this.back = null;
            this.size--;
            return currData;
        } else {
            Node<T> curr = this.back;
            T currData = curr.data;
            this.back = this.back.prev; 
            this.back.next = null;
            this.size--;
            return currData;
        }
    }

    @Override
    // return the value of the given index
    public T get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> curr = goTo(index);
        return curr.data;       
    }

    @Override
    // go to ith index, change the data to the item
    public void set(int index, T item) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        delete(index);
        insert(index,item);
    }

    @Override
    // go to ith index, add the item, move leftover items to the right
    public void insert(int index, T item) { //insert on the first, still have problem
        if (index < 0 ||index > this.size) {
            throw new IndexOutOfBoundsException();           
        }
        if (index == 0) {
            Node<T> curr = this.front;
            this.front = new Node<T>(null, item, curr);
        }
        System.out.println(this.front == null);
        Node<T> curr = goTo(index-1);
        Node<T> temp = goTo(index);
        curr.next = new Node<T>(curr,item,temp);
        this.size++;
    }

    @Override
    // delete the certain index
    public T delete(int index) { //delete at index 0, problematic
        if(index < 0 ||index >= this.size) {
            throw new IndexOutOfBoundsException();           
        } else if (index > 0) {
            Node<T> curr = goTo(index);
            Node<T> temp = goTo(index-1);
            temp.next = temp.next.next;
            //curr.next.prev = temp;
            //Ziwu: this line is possibly useful because I don't know if .prev will be enough to make one node exist.
            this.size--;
            return curr.data;
        } else {// the case for index to be 0
            Node<T> curr = goTo(index);
            curr = curr.next;
            //curr.prev = null;
            this.size--;
            return curr.data;
        }
    }

    @Override
    // return index of certain item
    public int indexOf(T item) {
        Node<T> curr = this.front;
        int count = 0;        
        while(curr.data != item && curr.next != null) {
            curr = curr.next;
            count++;
        }  
        if (curr.data == item) {
            return count;
        } else {
            return -1;
        }
    }

    @Override
    // return size
    public int size() {
        // if update size in every method?
        return this.size;
    }

    @Override
    // if it contains the item
    public boolean contains(T other) {
       return indexOf(other) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return (next() != null);
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            if (this.current.next == null) {
                throw new NoSuchElementException();
            }
            return (T) current.next;
        }
    }
}

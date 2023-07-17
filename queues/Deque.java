/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node first;
    private Node last;

    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
            size++;
            return;
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        oldFirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
            size++;
            return;
        }
        Node newLast = new Node();
        newLast.item = item;
        last.next = newLast;
        newLast.prev = last;
        last = last.next;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (this.size() == 1) {
            Node oldFirst = last;
            last = first = null;
            size--;
            return oldFirst.item;
        }
        Node oldFirst = first;
        first = first.next;
        first.prev = null;
        size--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (this.size() == 1) {
            Node oldLast = last;
            last = first = null;
            size--;
            return oldLast.item;
        }

        Node oldLast = last;
        last = last.prev;
        last.next = null;
        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node i = first;

        public boolean hasNext() {
            return (i != null);
        }

        public Item next() {
            if (i == null) {
                throw new NoSuchElementException();
            }
            Node old = i;
            i = i.next;
            return old.item;
        }

        public void remove() {
            // not supported
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(5);
        d.addFirst(4);
        d.addFirst(3);
        d.addLast(6);
        d.addLast(7);
        d.addFirst(2);
        d.addLast(8);

        d.removeLast();
        d.removeLast();
        d.removeFirst();
        d.removeFirst();

        for (Integer i : d) {
            System.out.println(i);
        }
    }
}

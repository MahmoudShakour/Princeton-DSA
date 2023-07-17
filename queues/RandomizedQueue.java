/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int last;
    private int capacity;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        last = 0;
        capacity = 1;

        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (last == capacity) {
            // printIt(items);
            resize(2 * capacity);
            // printIt(items);
        }
        items[last++] = item;
        size++;
    }


    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        int j = 0;
        for (int i = 0; i < last; i++) {
            if (items[i] != null) {
                newItems[j++] = items[i];
            }
        }
        items = newItems;
        last = j;
        capacity = newSize;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        while (true) {
            int rand = StdRandom.uniformInt(last);
            if (items[rand] != null) {
                Item item = items[rand];
                items[rand] = null;
                size--;
                if (size > 0 && size < capacity / 4) resize(capacity / 2);
                return item;
            }
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        while (true) {
            int rand = StdRandom.uniformInt(last);
            if (items[rand] != null) {
                return items[rand];
            }
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] elements;
        private int iteratorLast = 0;
        private int iterator = 0;

        public RandomizedQueueIterator() {
            elements = (Item[]) new Object[size];
            for (int x = 0; x < last; x++) {
                if (items[x] != null) {
                    elements[iteratorLast++] = items[x];
                }
            }
            StdRandom.shuffle(elements);
        }

        public boolean hasNext() {
            return iterator < iteratorLast;
        }

        public Item next() {
            if (iterator >= iteratorLast) {
                throw new NoSuchElementException();
            }

            return elements[iterator++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);

        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());


        StdOut.println();

        for (Integer i : q) {
            StdOut.println(i);
        }

        StdOut.println(q.sample());


    }

}

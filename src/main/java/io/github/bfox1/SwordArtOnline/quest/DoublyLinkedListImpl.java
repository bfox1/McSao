package io.github.bfox1.SwordArtOnline.quest;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class DoublyLinkedListImpl<E> implements Iterable<E>
{
    private Node head;
    private Node tail;
    private int size;


    public DoublyLinkedListImpl()
    {
        size = 0;
    }

    @Override
    public ListIterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class Node
    {
        E element;
        Node next;
        Node prev;
        int position;

        public Node(E element, Node next, Node prev, int position)
        {
            this.element = element;
            this.next = next;
            this.prev = prev;
            this.position = position;
        }
    }


    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }


    public void addFirst(E element)
    {
        Node tmp = new Node(element, head, null, 0);
        if(head != null)
        {
            head.prev = tmp;
            head.position = 1;
        }

        this.head = tmp;

        if(tail == null)
        {
            this.tail = tmp;
        }
        size++;
        System.out.println("adding: " + element);
    }

    public void addLast(E element)
    {
        Node tmp = new Node(element, null, tail, size+1);
        if(tail != null)
        {
            tail.next = tmp;
        }
        tail = tmp;
        if(head == null)
        {
            head = tmp;
        }
        size++;
        System.out.println("adding Element to last: " + element);
    }



    public void iterateForward()
    {
        System.out.println("Iterating forward");
        Node tmp = head;
        while (tmp != null)
        {
            System.out.println(tmp.element);
            tmp = tmp.next;
        }
    }


    public void interatebackwards()
    {
        System.out.println("Iterating Backward");
        Node tmp = tail;
        while (tmp != null)
        {
            System.out.println(tmp.element);
            tmp = tmp.prev;
        }
    }

    public E removeFirst()
    {
        if(size == 0)
        {
            throw new NoSuchElementException();
        }
        Node tmp = head;
        head = head.next;
        head.prev = null;
        size--;
        System.out.println("deleted:" + tmp.element);
        return tmp.element;
    }

    public E removeLast()
    {
        if (size == 0) throw new NoSuchElementException();
        Node tmp = tail;
        tail = tail.prev;
        tail.next = null;
        size--;
        System.out.println("deleted: "+tmp.element);
        return tmp.element;
    }


    public void getNext()
    {

    }

    public class DoublyLinkedListIterator implements ListIterator<E>
    {
        private Node current = head;
        private Node lastAccessed = null;

        private int index = 0;

        public boolean hasNext()      { return index < size; }
        public boolean hasPrevious()  { return index > 0; }
        public int previousIndex()    { return index - 1; }
        public int nextIndex()        { return index;     }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastAccessed = current;
            E item = current.element;
            current = current.next;
            index++;
            return item;
        }

        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            current = current.prev;
            index--;
            lastAccessed = current;
            return current.element;
        }

        // replace the item of the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void set(E item) {
            if (lastAccessed == null) throw new IllegalStateException();
            lastAccessed.element = item;
        }

        // remove the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void remove() {
            if (lastAccessed == null) throw new IllegalStateException();
            Node x = lastAccessed.prev;
            Node y = lastAccessed.next;
            x.next = y;
            y.prev = x;
            size--;
            if (current == lastAccessed)
                current = y;
            else
                index--;
            lastAccessed = null;
        }

        // add element to list
        public void add(E item) {
            Node x = current.prev;
            Node y = new Node(item, current, x, 0);
            Node z = current;
            y.element = item;
            x.next = y;
            y.next = z;
            z.prev = y;
            y.prev = x;
            size++;
            index++;
            lastAccessed = null;
            System.out.println(current.element);
        }
    }

}

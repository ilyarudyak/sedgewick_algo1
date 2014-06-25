import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a 
 * generalization of a stack and a queue. It supports inserting 
 * and removing items from either the front or the back of the 
 * data structure. 
 */
public class Deque<Item> implements Iterable<Item> {

    private Object[] items;
    // number of items in deque
    private int size;

    // for queue
    private int head;
    private int tail;


    private static final int INITIAL_CAPACITY = 2;


    public Deque(){

        items = new Object[INITIAL_CAPACITY];
        size = 0;
        head = 0;
        tail = 0;

    }
    // is it empty
    public boolean isEmpty(){ return size == 0; } 

    // return the number of items on the deque
    public int size(){ return size; }  

    private void printDeque(){
        for (int i=0; i<items.length; i++)
            StdOut.print(items[i] + " ");
        
        StdOut.println();
    }
  
    // ------------------- FIRST -------------------

    // insert the item at the front --NEW
    public void addFirst(Item item){

        if (size == items.length)
            resize(size * 2);

        if (item == null)
            throw new NullPointerException();

        if (head == 0)
           head = items.length - 1;
        else head--;

        items[head] = item;
        //head = (head + 1) % items.length;
        size++;

    }

    // delete and return the item at the front --OK
    public Item removeFirst(){
        
        if (isEmpty())
            throw new NoSuchElementException(); 
    
        Item item = (Item) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        size--;

        // shrink size of array if necessary
        if (size > 0 && size == items.length/4) 
            resize(items.length/2);

        return item;
            
    }   

    // ------------------- FIRST -------------------


    // ------------------- LAST -------------------

    // insert the item at the end --OK
    public void addLast(Item item){

        if (item == null)
            throw new NullPointerException();
        
        if (size == items.length)
            resize(size * 2);
        
        items[tail] = item;
        tail = (tail + 1) % items.length;
        size++;
         
    }  



    // delete and return the item at the end --NEW
    public Item removeLast(){

        if (isEmpty())
            throw new NoSuchElementException(); 

        if (tail == 0)
            tail = items.length - 1;
        else tail--;
        
        Item item = (Item) items[tail];
        size--;

        // shrink size of array if necessary
        if (size > 0 && size == items.length/4) 
            resize(items.length/2);


        return item;
    }

    // ------------------- LAST -------------------

    // --OK
    private void resize(int capacity){
        Object[] tmp = new Object[capacity];
        int n = capacity + 1;

        for (int i=0; i<size; i++)
            tmp[i] = items[(head+i) % items.length];
        items = tmp;
        
        tail = size ;
        head = 0;

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i;

        public ArrayIterator() {
            i = head;
        }

        public boolean hasNext() {
            return i < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (i == items.length)
                i = 0;
            return (Item) items[i++];
        }
    }

    // unit testing
    public static void main(String[] args){
        Deque<String> s = new Deque<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.addFirst(item);
            else if (!s.isEmpty()) StdOut.print(s.removeFirst() + " ");
            
            //s.printDeque();
        }
        StdOut.println("(" + s.size() + " left on stack)");
    } 
}

import java.util.*;

public class QueueSort{




    public static <E extends Comparable<E>> Deque<E> sort(ArrayList<E> a){
        
        // create queue of queues
        Deque<Deque<E>> q = new LinkedList<Deque<E>>();

        // fill it by 1-element queues
        for (E e : a){
            Deque<E> q0 = new LinkedList<E>();
            q0.add(e);
            q.add(q0);
        }

        while (q.size() > 1){
            q.add( merge(q.poll(), q.poll()) );
        }
        return q.poll();
    }

    /** takes two queues of sorted items as arguments 
     * and returns a queue that results from merging 
     * the queues into sorted order */
    static <E extends Comparable<E>> Deque<E> merge(Deque<E> q1, 
                                                Deque<E> q2){

        Deque<E> q = new LinkedList<E>();

        int n = q1.size()+q2.size();
        for (int i=0; i < n; i++){
            
            if (q1.isEmpty())                               q.add(q2.poll());
            else if (q2.isEmpty())                          q.add(q1.poll());
            else if (q1.peek().compareTo(q2.peek()) < 0)    q.add(q1.poll());
            else                                            q.add(q2.poll());


        }
        return q;
    }
} // end of class

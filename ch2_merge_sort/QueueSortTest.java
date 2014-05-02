import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class QueueSortTest {


	@Test
	public void sortTest() {

        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(5);
        a.add(4);
        a.add(3);
        a.add(2);
        a.add(1);
        

        Assert.assertArrayEquals(
                new Integer[]{1,2,3,4,5},
                QueueSort.sort(a).toArray()
                );
    }

	@Test
	public void mergeTest() {

		Deque<Integer> q1 = new LinkedList<Integer>();
        q1.add(1);
        q1.add(20);
        q1.add(30);

        Deque<Integer> q2 = new LinkedList<Integer>();
        q2.add(2);
        q2.add(3);
        q2.add(10);

        Deque<Integer> q = new LinkedList<Integer>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(10);
        q.add(20);
        q.add(30);


		Assert.assertArrayEquals(q.toArray(), 
                QueueSort.merge(q1,q2).toArray());
	}

}

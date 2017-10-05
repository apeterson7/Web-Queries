import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class MyPriorityQueueTest {

    @Test
    public void testSize() {
	MyPriorityQueue<Integer> test = new MyPriorityQueue<Integer>(new IntComparator());

	for(int i = 0; i < 5; i++) {
	    test.offer(i);
	}

	assertTrue(test.size() == 5);
    }

    @Test
    public void testClear() {
	fail("Not yet implemented");
    }

    @Test
    public void testSetComparator() {
	fail("Not yet implemented");
    }

    @Test
    public void testComparator() {
	fail("Not yet implemented");
    }

    @Test
    public void testOffer() {
	MyPriorityQueue<Integer> test = new MyPriorityQueue<Integer>(new IntComparator());
	test.offer(10);
	test.offer(50);
	test.offer(30);
	test.offer(40);
	test.offer(60);
	
	for(int i = 10; i <= 60; i+=10) {
	    System.out.println(test.peek());
	    assertTrue(test.poll()==i);
	}

    }

    @Test
    public void testPeek() {
	fail("Not yet implemented");
    }

    @Test
    public void testPoll() {
	fail("Not yet implemented");
    }

    @Test
    public void testIterator() {
	fail("Not yet implemented");
    }

}

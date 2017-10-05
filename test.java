import java.io.IOException;
import java.util.PriorityQueue;


/**
 * 
 * @author apeterso
 *for testing purposes
 *
 */

public class test {

    public static void main(String[] args){
	MyPriorityQueue<Integer> test = new MyPriorityQueue<Integer>(new IntComparator());
	PriorityQueue<Integer> real = new PriorityQueue<Integer>(1, new IntComparator());
	test.offer(10);
	real.offer(10);
	System.out.println(test.getHeap());
	test.offer(50);
	real.offer(50);
	System.out.println(test.getHeap());
	test.offer(30);
	real.offer(30);
	System.out.println(test.getHeap());
	test.offer(40);
	real.offer(40);
	System.out.println(test.getHeap());
	test.offer(100);
	real.offer(100);
	System.out.println(test.getHeap());
	test.offer(20);
	real.offer(20);
	System.out.println(test.getHeap());
	
	test.setComparator(new RevIntComparator());
	System.out.println(test.getHeap());
	
	
	for(int i = 0; i < 6; i++) {
	    System.out.println(test.poll());
	    System.out.println(test.getHeap());
	}
	/*
	
	System.out.println();
	System.out.println(real.toString());
	for(int i = 0; i < 7; i++) {
	    System.out.println(real.poll());
	    System.out.println(real.toString());
	}*/
    }
}

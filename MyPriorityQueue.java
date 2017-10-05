/**
 * TODO - add some comments here.
 *
 * @author Benjamin Kuperman (Spring 2011)
 * @author YOU!
 */

import java.util.*;

public class MyPriorityQueue<AnyType> extends AbstractQueue<AnyType> {

    /** Item to use for making comparisons */
    private Comparator<? super AnyType> cmp;

    /** ArrayList for the heap itself */
    private ArrayList<AnyType> heap;

    /**
     * for testing purposes
     * @return heap
     */
    public ArrayList<AnyType> getHeap(){
	return heap;
    }
    
    public void setComparator(Comparator<AnyType> cmp) {
        this.cmp = cmp;
        for(int i = heap.size()-1; i > 0; i--) {
            this.percolateUp(i);
        }
    }

    
    /**
     *
     * @return the given comparator
     */
    public Comparator<? super AnyType> comparator() {
        return this.cmp;
    }

    /**
     * 
     * @return the size of the priorityqueue
     */
    public int size() {
        // TODO - complete this
        return heap.size()-1;
    }

    /**
     *  clears the priorityqueue
     */
    public void clear() {
        heap = new ArrayList<AnyType>();
    }

    /**
     * adds object o to the priority queue and percolates it up to the correct position in the heap
     * 
     * @param o
     * @return 
     */
    public boolean offer(AnyType o) throws NullPointerException{

        if(this.size() == -1) {
            heap.add(null);
            heap.add(o);
        } else {
            heap.add(o);
            this.percolateUp(this.size());  
        }
        return true;
    }
    
    /**
     * 
     * @return the top item in the priority queue
     */
    public AnyType peek() {
        // TODO - complete this
        return heap.get(1);
    }

    /**
     * 
     * @return the top item in the priority queue then deletes it from the queue, 
     * percolating the hole down through the priority queue
     */
    public AnyType poll() {
        // TODO - complete this
        AnyType temp = heap.get(1);
	heap.set(1,null);

        this.percolateDown(1);
        heap.remove(heap.size()-1);
        
        return temp;
    }

    
    public Iterator<AnyType> iterator() {
        // TODO - complete this
        return heap.iterator();
    }
    
    /**
     * 
     * @param hole - the index of the hole in the priority queue
     * recursively brings the hole in the priority queue down to the bottom of the binary tree
     */
    private void percolateDown(int hole) {
	
	if(lchild(hole) >= heap.size() && rchild(hole) >= heap.size()) {
	    Collections.swap(heap, heap.size()-1, hole);    
	    
	} else if(lchild(hole) < heap.size() && rchild(hole) >= heap.size()) {
	    Collections.swap(heap, lchild(hole), hole);
	    this.percolateDown(lchild(hole));
	    
	}else if(lchild(hole) >= heap.size() && rchild(hole) < heap.size()) {
	    Collections.swap(heap, rchild(hole), hole);
	    this.percolateDown(rchild(hole));
	    
	}else if(lchild(hole) < heap.size() && rchild(hole) < heap.size()) {
	    if(cmp.compare(heap.get(lchild(hole)), heap.get(rchild(hole))) < 0) {
		Collections.swap(heap,lchild(hole),hole);
		this.percolateDown(lchild(hole));
	    }else {
		Collections.swap(heap, rchild(hole), hole);
		this.percolateDown(rchild(hole));
	    }
	}
    }
    
    /**
     * 
     * @param hole - the index of the hole in the priority queue to be percolated up
     * moves an item up to the correct position in the priority queue
     * 
     */
    private void percolateUp(int hole) {
	while((heap.get(parent(hole)) != null) && cmp.compare(heap.get(parent(hole)), heap.get(hole)) > 0) {
	    Collections.swap(heap,parent(hole),hole);
	    hole = parent(hole);
	}
    }

    /**
     * Construct a heap with a given comparator.
     * @param cmp Comparator to use for ordering nodes
     */
    public MyPriorityQueue(Comparator<? super AnyType> cmp) {
        // TODO
        
	this.heap = new ArrayList<AnyType>();
        this.cmp = cmp;
        
    }


    /**
     * Calculate the parent index of a node in a complete binary tree
     * 
     * @param index
     *            node to find parent index of
     * @return index of parent or -1 if there is no parent
     */
    private int parent(int index) {
	if(index == 1) {
	    return 0;
	}
	else if(index%2 == 1) {
            return (index-1)/2;
        }else {
            return index/2;
        }
    }

    /**
     * Calculate the index for the left child of the given index in a complete
     * binary tree.
     * @param index node to find left child of
     * @return index of left child or -1 if there is no left child
     */
    private int lchild(int index) {
        // TODO - complete this
        return index*2;
    }

    /**
     * Calculate the index for the right child of the given index in a complete
     * binary tree.
     * @param index node to find right child of
     * @return index of right child or -1 if there is no right child
     */
    private int rchild(int index) {
        // TODO - complete this
        return index*2+1;
    }

}

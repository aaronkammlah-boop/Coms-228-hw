package edu.iastate.cs2280.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * @author [redacted]
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }
/**
 * returns the total amount of elements in the list
 */
  @Override
  public int size()
  {
    
    return size;
  }
  /**
   * adds an element to the end of a list if there is room
   * if there isnt create a new 
   */
  @Override
  public boolean add(E item)
  {
	  //throws if null
  if(item == null) {
	  throw new NullPointerException();
  }
  //if the list is empty place a item at offset 0
  if(size==0) {
	  Node n = new Node();
	  n.addItem(item);
	  links(head,n);
  }else {
	  // current node
	  Node last = tail.previous;
	  // if node isnt full add node into it
	  if(last.count < nodeSize) {
		  last.addItem(item);
	  }else {
		  // if node is full make a new node puts at offset 0
		  Node n = new Node();
		  n.addItem(item);
		  links(last,n);
	  }
  }
    size++;
    return true;
  }
/**
 * adds an element at a given location. this moves
 * elements to the right 
 */
  @Override
  public void add(int pos, E item)
  {
	  // throw if null 
	  if(item == null) {
		  throw new NullPointerException();
	  } 
	  //checks if pos is in bounds
	  if(pos <0 || pos> size) {
		  throw new IndexOutOfBoundsException();
	  }
	  // inserts at the end
	  if(pos == size) {
		  add(item);
		  return;
	  }// uses node info to read the info and insert the item
	  
	  NodeInfo info = find(pos);
	  add(info.node, info.offset, item);
	  size++;
  }
  /**
   * class pairs aa node and an offset into nodes daata array
   * 
   */
  private class NodeInfo{
		public Node node;
		public int offset;
		/**
		 * makes a node info with thhe given node info and offset 
		 * @param node node containing element
		 * @param offset offset of element in node (slot of the node)
		 */
  public NodeInfo(Node node, int offset) {
		this.node = node;
		this.offset=offset;
	}
}
  /**
   * used to find the node and offset for a given index
   * gets the elements counts to traverse the index
   * @param pos index 
   * @return a NodeInfo with the node and offset
   */
public NodeInfo find(int pos) {
	//end of list sets to tail offset 0
	if(pos == size) {
		return new NodeInfo(tail,0);
	}
	//runs through the nodes checks if pos is in the given node
	Node current = head.next;
	int seen = 0;
	while(current != tail) {
		//if pos is in node return current node and offset = pos-seen
		if(pos < seen+ current.count) {
			return new NodeInfo(current, pos - seen);
		}
		// gets nodes element count and goes to next node
		seen += current.count;
		current = current.next;
	}
	//end of list
	return new NodeInfo(tail,0);
}
/**
 * adds an item at the location given by the node and offset 
 * rules: if list is empty new node is created
 * if offset is 0 and the predecessor node has room item is added to that node
 * if target node has room the item is inserted at the offset
 * if the target node is full its seconded half is split into a new node 
 * item is placed in the node with the offset
 * @param n the node where to insert at
 * @param offset offset within node
 * @param item element to insert
 * @return Nodeinfo describing final loc of inserted element
 */
private NodeInfo add(Node n, int offset, E item) {
	// first rule empty list creates a new node 
	if(size == 0) {
		Node newnode = new Node();
		newnode.addItem(item);
		links(head,newnode);
		return new NodeInfo(newnode,0);
	}
	//rule two offset is 0 and the predecessor node has room add node to thay node
		if(offset==0 && n.previous != head && n.previous != null&& n.previous.count <nodeSize) {
			Node pre = n.previous;
			int givenoff = pre.count;
			pre.addItem(item);
			return new NodeInfo(pre,givenoff);
		}// there is space in node n so inserts at offset
		  if(n != tail && n.count < nodeSize) {
		        n.addItem(offset, item);
		        return new NodeInfo(n, offset);
		    }// node is full splits node in half (2 nodes) places element in where the offset is
		    Node newnode = new Node();
		    //copies second half of n to new node
		    for(int i = nodeSize/2; i < nodeSize; i++) {
		        newnode.addItem(n.data[i]);
		        // clears moved slot in n
		        n.data[i] = null;
		    }// connects n and the new node
		    n.count = nodeSize/2;
		    links(n, newnode);
		    // offset is <= m/2 stays in n
		    if(offset <= nodeSize/2) {
		        n.addItem(offset, item);
		        return new NodeInfo(n, offset);
		        //else its placed into the new node
	}else {
		int newoff = offset - nodeSize/2;
		newnode.addItem(newoff, item);
		return new NodeInfo(newnode, newoff);
	}
	
}
/**
 * (m = nodesize)
 * Removes element at given pos using the given rules 
 * rules: if n is the last node and has only one element get rid of node
 * else if n is the last node (has more than 1 element) or n has more than m/2 elements 
 * remove the element from n and shift the rest
 * else n has m/2 elements looks at the next node and rebalences
 * if new node has more than m/2 elements move new nodes first element to the old node
 * if less than m/2 move the new nodes elements to the old one and delete the new node
 * @param pos given index of the element to remove
 * @return removed element
 */
  @Override
  public E remove(int pos)
  {
	  // throw if outofbound
    if(pos < 0 || pos>= size) {
    	throw new IndexOutOfBoundsException();
    }
    //finds the node and offset for pos
    NodeInfo info = find(pos);
    Node n = info.node;
    int offset = info.offset;
    E remove = n.data[offset];
    //if n is the last node or n has more than node size /2 elements removes the node
    if(n.next == tail || n.count> nodeSize/2) {
    	n.removeItem(offset);
    	if(n.count == 0) {
    		unlink(n);
    	}
    }
    else {
    	// else remove the element and if new node more than m/2 move new nodes first element
    	//to the old node else copies the new nodes elements to the old node then delete the new node
    	n.removeItem(offset);
    	Node next = n.next;
    	if(next.count > nodeSize /2) {
    		n.addItem(next.data[0]);
    		next.removeItem(0);
    	}else {// copies new node to old node
    		for(int i = 0; i <next.count; i++) {
    			n.addItem(next.data[i]);
    		}
    		unlink(next);
    	}
    }
    size--;
    return remove;
  }
  /**
   * inserts new node into the chain after old node updates the links for 
   * eaach effected node
   * @param oldnode existing node that its inserted after
   * @param newnode new node that inserted
   */
  public void links(Node oldnode, Node newnode) {
	  newnode.previous = oldnode; // new nodes prev link is the oldnode
	  newnode.next = oldnode.next;// new node front is oldnods front
	  oldnode.next.previous = newnode;// node in front of old node connects back to newnode
	  oldnode.next = newnode; // oldnode connects to front to newnode
  }
  /**
   * unlinks a node from the list
   * @param node node to remove
   */
  public void unlink(Node node) {
	  node.previous.next = node.next; //previous node skips this node
	  node.next.previous = node.previous; // next node skips this node
  }
  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  // nothing to sort
	  if(size == 0) {
		  return;
	  }
	  //creates an array to hold elements and go through the nodes
	  E[] array = (E[]) new Comparable[size];
	  int i =0;
	  Node curr = head.next;
	  while(curr != tail) {
		  //copies the elements
		  for( int j = 0; j< curr.count; j++) {
			  array[i++] = curr.data[j];
		  }
		  curr = curr.next;
	 
	  
		  
	  }
	  head.next = tail;
	  tail.previous = head;
	  size = 0;
	  //sorts in ascending order in array
	  insertionSort(array, new ElementComparator());
	  
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {	//nothing to sort
	  if(size == 0) {
		  return;
	  }
	//creates an array to hold elements and go through the nodes
	  E[] array = (E[]) new Comparable[size];
	  int i = 0;
	  Node curr = head.next;
	  while(curr != tail) {
		  // copies each element
		  for(int j =0; j < curr.count; j++) {
			  array[i++] = curr.data[j];
		  }
		  curr = curr.next;
	  }
	  head.next= tail;
	  tail.previous = head;
	  size = 0;
	  // sort in descending order
	  bubbleSort(array);
  }
  
  @Override
  public Iterator<E> iterator()
  {
   
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
   
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
    
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      
    }
    	
    		
    		
    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 /**
  * an iterator that uses an aray to build from a list uses a
  * cursor to go between elements uses add and remove functions
  * to modify the list
  */
  private class StoutListIterator implements ListIterator<E>
  {
	  
	final int back = -1;
	
	final int none = 0;
	final int front = 1;
	public  int currpost;
	public int last;
	public E[] dataArray;
    /**
     * Default constructor 
     */
	// puts position with cursor 0
    public StoutListIterator()
    {
    	this(0);
    }

    /**
     * Constructor finds node at a given position.
     * @param pos starting cursor position
     */
   

    public StoutListIterator(int pos)
    {
    	// makse sure its inbounds
    	if(pos < 0 || pos > size) {
    		throw new IndexOutOfBoundsException();
    	}
 // creates a plain  and creates a starting point 
    	dataArray=(E[]) new Comparable[size];
    	int i = 0;
    	Node curr = head.next;
    	//goes through the nodes
    	while(curr != tail) {
    		//copies the elements 
    		for(int j = 0; j < curr.count; j++) {
    			dataArray[i++] = curr.data[j];
    		}
    		curr= curr.next; // next node
    	}
    	//sets the cursor 
    	currpost = pos;
    	//dont set or remove
    	last = none;
    }
/**
 * checks is a next element
 * @return true if there is another element
 */
    @Override
    public boolean hasNext()
    {
    	return currpost < size;
    }
    /**
     * return the next element and moves cursor up one
     * @return next element
     */
    @Override
    public E next()
    {
    	// if hasnext was faalse throw exception
    	if(!hasNext()) {
    		throw new NoSuchElementException();
    	}
    	// reads the element moves the curser up one and returns the element
    	E ret = dataArray[currpost];
    	currpost++;
    	last = back;
    	return ret;
    }
    /**
     * checks if there is an element before cursors loc
     * @return previous element
     */
@Override
public boolean hasPrevious() {
	return currpost >0;
}
/**
 * moves the cursor back one position and returns the element
 * @return prev element
 */
@Override
public E previous() {
	// nothing behind the cursor
	if(!hasPrevious()) {
		throw new NoSuchElementException();
	}
	// moves the cursor back one and returns the element
	currpost--;
	last =front;
	return dataArray[currpost];
}
/**
 * @return the index infront of the current element
 * 
 */
@Override
public int nextIndex() {
	return currpost;
	}
/**
 * @return the prev index of the element
 */
@Override
public int previousIndex() {
	return currpost - 1;
	}
/**
 * replaces the last element returned (next or previous) with given element
 * @param ele replacment element
 * 
 */
@Override
public void set(E ele ) {
	//no element returned 
	if(last == 0) {
		throw new IllegalStateException();
	}
	// throws if null
	if(ele  == null) {
		throw new NullPointerException();
	}
	int loc;
	//checks if it was previous or back and sets the locaation
	if(last == back) {
		loc = currpost -1;
	}else {
		loc = currpost;
	}// replaces the element in the node
	NodeInfo info = find(loc);
	info.node.data[info.offset] = ele;
	dataArray[loc]= ele;
}


@Override
public void add(E item) {
	
}
    @Override
    public void remove() {
   
    }
    
   
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  
	  for(int i =1; i < arr.length; i++) {
		  E ele = arr[i];
		  int j = i-1;
		  while(j >= 0 && comp.compare(arr[j], ele)>0){
			  arr[j+1] = arr[j];
			  j--;
  }
		  arr[j+1] = ele;
  }
  }
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  for(int i = 0; i < arr.length -1; i++) {
	for(int j = 0; j < arr.length -1 -i; j++ ) {
		if(arr[j].compareTo(arr[j+1])<0) {
			E temp = arr[j];
			arr[j] = arr[j+1];
			arr[j+1] = temp;
		}
  }
  }
}
  /**
   * comparator orders elements in non decreasing order (used by sort())
   */
  private class ElementComparator implements Comparator <E>{

	  @Override
	  public int compare(E o1, E o2) {
		
		return o1.compareTo(o2);
	  }
	  }
}
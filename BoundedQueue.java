class BoundedQueue {
    private SListNode head;
    private SListNode tail;
    private int size;
    private int total;

    public BoundedQueue(int number) {
    	head = null;
    	tail = null;
    	size = number; //size of queue
    	total = 0; //number of integers
    }

  	public int total() {
  		return total;
  	}

  	public int size() {
  		return size;
  	}
  
  	public boolean isEmpty() {
  		return total == 0;
  	}
  
    public class QueueEmptyException extends Exception {  //should be good
		public QueueEmptyException() {
    		super();
  		}
  		public QueueEmptyException(String s) {
    		super(s);
  		}
    } 

  	public void enqueue(int number) {   //add number at the end
  		SListNode element = new SListNode(number);
  		if (head == null && tail == null) {
  		//SListNode element = new SListNode(number); //change name to element
        //incrementInt();
        	tail = head = element; // head = tail =  element;
  		} else {
  			if (this.size() == this.total()) {
  				System.out.println("Error: trying to add item to full list");
  			}
  			tail.next = element; //new SListNode(number);
  			tail = tail.next;	
  		}
  		total++;
  	}
  
  	public int dequeue() throws QueueEmptyException {
  		//SListNode element;
  		if (head == null && tail == null) {
  			throw new QueueEmptyException();
  		//} else if (head.next == null && head == tail) {  //added this case?
  			//element = head;
  			//head = tail = null; 
  			//return head.item;
  		} else {
  			int number = head.item;
  			head = head.next;
  			total--;
  			if (total == 0) {
  				tail = null;
  			}
  			return number;
  		}
  	}
  
  	public int front() throws QueueEmptyException {
  		if (head == null) {
  			throw new QueueEmptyException();
  		} else {
  			return head.item;
  		}
  	}

  	public String toString() {
  		String result = "[ ";
  		try {
  			for (int i = 0; i < total(); i++) { 
  				result += front() + " ";
  				enqueue(dequeue()); 
  			}
  		} catch (QueueEmptyException e) {
  			System.err.println("Error: You are trying to dequeue from empty queue.");
  		}
  		return result + "]";
  	}

  	/* Data Structure representing a Singly Linked List */
	
	/*class SList {  
    private SListNode head; // First node in list
		private SListNode tail; // tail reference of the list which points to null since not circularly linked list 
		private int size; // total number of ListNodes

		public SList() { // represents empty SList
			head = null;
			size = 0;
		}
	} */
 
	class SListNode {
		private int item; // number in this LinkedNode
		private SListNode next; // next reference of LinkedNode

		/* SListNode (with 1 parameter) constructs a list node referencing the item "number" */
		SListNode(int number) { 
			item = number;
			this.next = null;
		}

		/* SListNode (with 2 parameters) constructs a list node referencing the item "number" 
		and whose next list node is to be "next" */
		SListNode(int number, SListNode next) {
			item = number;
			this.next = next;
		}
	}

	/* Test Code */
	public static void main(String[] args) {
    	try {
		  System.out.println("Testing constructor");
		  BoundedQueue first = new BoundedQueue(4);
      	  System.out.println("First error okay, since toString method dequeues a number but no number present");
  		  System.out.println(first.toString());
  		  System.out.println("Testing isEmpty");
  		  System.out.println("You be true: " + first.isEmpty());
  		  System.out.println("Testing size of queue, should be 4: " + first.size());
	  	  System.out.println("Testing enqueue");
	  	  first.enqueue(3);
	      System.out.println(first.toString());
	  	  System.out.println("Testing number of items in queue");
	  	  System.out.println("Should be 1: " + first.total());
	  	  System.out.println("Testing front, should be 3: " + first.front());
	  	  first.enqueue(5);
	  	  System.out.println(first.toString());
	  	  System.out.println("Testing number of items in queue");
	  	  System.out.println("Should be 2: " + first.total());
	  	  System.out.println("Testing isEmpty");
	  	  System.out.println("Should be false: " + first.isEmpty());
	  	  System.out.println("Testing dequeue");
	  	  first.dequeue();
	  	  System.out.println(first.toString());
	  	  System.out.println("Testing front, should be 5: " + first.front());
	  	  System.out.println("Adding 3 numbers");
	  	  first.enqueue(4);
	  	  first.enqueue(88);
	  	  first.enqueue(10);
	  	  System.out.println(first.toString());
	  	  System.out.println("Testing size of queue, should be 4: " + first.size());
	  	  System.out.println("Testing number of items in queue");
	  	  System.out.println("Should be 4: " + first.total());
	  	  System.out.println("Adding 1 number but queue full so should give notice");
	  	  first.enqueue(6);
	  	  System.out.println("not sure if call to toString necessary");
	  	  System.out.println("Testing constructor again");
	  	  BoundedQueue second = new BoundedQueue(0);
	  	  System.out.println(second.toString());
	  	  second.enqueue(100);
	  	  System.out.println(second.toString());
	  	  System.out.println("Testing front, should be 100: " + second.front());
	  	  System.out.println("Trying to dequeue to much should give Exception");
	  	  second.dequeue();
	  	  System.out.println("Still good");
	  	  System.out.println(second.toString());
	  	  System.out.println("Now we hit Exception");
	  	  second.dequeue();
    	} catch (QueueEmptyException q) {
      	  System.out.println(q);
    	}
    }
}	


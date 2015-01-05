/* LockDList.java */
package list;

class LockDList extends DList {
  protected LockDListNode head;

  protected LockDList() {
  	super();
  }
  protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
    return new LockDListNode(item, prev, next);	
  } 	
  public void remove(DListNode node) {
  	if (node != null && ((LockDListNode) node).locked == false) {
  	  super.remove(node);
  	}
  }
  public void lockNode(DListNode node) {
    ((LockDListNode)node).locked = true;
  }

public static void main(String[] args) {
    System.out.println("Testing Constructor");
	LockDList testList = new LockDList();
	System.out.println("Is empty? Should be true: " + testList.isEmpty());
	System.out.println("Should be zero length: " + testList.length());

	System.out.println("\nTesting insertFront");
	testList.insertFront(1);
	System.out.println("Is empty? Should be false: " + testList.isEmpty());
	System.out.println("Should be one length: " + testList.length());
	System.out.println("Should be [ 1 ]: " + testList);
	testList.insertFront(4);
	testList.insertFront(8);
	testList.insertFront(7);
	System.out.println(testList);

	LockDListNode currNode = (LockDListNode) ((DList) testList).front();
	currNode = (LockDListNode) testList.next(currNode);
	currNode = (LockDListNode) testList.next(currNode);
	//testList.lockNode(currNode);
	testList.remove(currNode);

	System.out.println(testList);
    }
}
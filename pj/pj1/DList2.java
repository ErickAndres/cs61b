/* DList2.java */

/**
 *  A DList2 is a mutable doubly-linked list.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList2 {

  /**
   *  head references the sentinel node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode2 head;
  protected long size;

  /* DList2 invariants:
   *  1)  head != null.
   *  2)  For any DListNode2 x in a DList2, x.next != null.
   *  3)  For any DListNode2 x in a DList2, x.prev != null.
   *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
   *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
   *      (denoted by "head"), that can be accessed from the sentinel by
   *      a sequence of "next" references.
   */

  /**
   *  DList2() constructor for an empty DList2.
   */
  public DList2() {
    head = new DListNode2();
    head.item = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
    head.next = head;
    head.prev = head;
    size = 0;
  }

  /**
   *  DList2() constructor for a one-node DList2.
   */
  public DList2(int[] a) {
    head = new DListNode2();
    head.item = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
    head.next = new DListNode2();
    head.next.item = a;
    head.prev = head.next;
    head.next.prev = head;
    head.prev.next = head;
    size = 1;
  }

  /**
   *  DList2() constructor for a two-node DList2.
   */
  public DList2(int[] a, int[] b) {
    head = new DListNode2();
    head.item = new int[4];
    head.next = new DListNode2();
    head.next.item = a;
    head.prev = new DListNode2();
    head.prev.item = b;
    head.next.prev = head;
    head.next.next = head.prev;
    head.prev.next = head;
    head.prev.prev = head.next;
    size = 2;
  }

  /**
   *  insertFront() inserts an item at the front of a DList2.
   */
  public void insertFront(int[] i) {
    DListNode2 first = new DListNode2(i);
    first.next = head.next;
    first.prev = head;
    head.next.prev = first;
    head.next = first;     
    size++;
  }
  /*public void insertAfter(DListNode2 curr, DListNode2 after) {
    curr.next.prev = after;
    after.next = curr.next;
    curr.prev = after;
    after.prev = curr;
    size++;

  }*/
  public void insertEnd(int[] i){
    DListNode2 last = new DListNode2(i);
    last.next = head;
    last.prev = head.prev;
    head.prev.next = last;
    head.prev = last;
    size++;
  }
  /**
   *  removeFront() removes the first item (and first non-sentinel node) from
   *  a DList2.  If the list is empty, do nothing.
   */
  public void removeFront() {
    if (size == 0) {
      return;
    } else {
      head.next = head.next.next;
      head.next.prev = head;  
    }
    size--; 
  }
  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode2 current = head.next;
    while (current != head) {
      result = result + current.item[0] + "  ";
      current = current.next;
    }
    return result + "]";
  }

  public static void main(String[] args) {
    // DO NOT CHANGE THE FOLLOWING CODE.
    DList2 list = new DList2();
    list.insertFront(new int[]{0,0,0,0});
    System.out.println(list);
    //DListNode2 next = new DListNode2(new int[]{1,1,1,1});
    list.insertEnd(new int[]{1,1,1,1});
    //System.out.println(list.head.next.next.item[0]);
    //DListNode2 prev = new DListNode2(new int[]{2,2,2,2});
    //list.insertBefore(next, prev);
    //System.out.println(list.head.next.next.item[0]);
    list.removeFront();
    System.out.println(list);
    
  }

}
/* ListSorts.java */
package graphalg;
import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue allQ = new LinkedQueue(); 
    LinkedQueue oneQ;   
    try {
      while (q.size() > 0) { 
        oneQ = new LinkedQueue();  
        oneQ.enqueue(q.dequeue());  
        allQ.enqueue(oneQ);  
      } 
    } catch (QueueEmptyException e) {
    }
    return allQ;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue mergeQ = new LinkedQueue();  
    Comparable itemQ1;
    Comparable itemQ2;
    if (q1.isEmpty() && q2.isEmpty()) {  
      return mergeQ;
    } else {
      try {
        while (q1.size() > 0 && q2.size() > 0) {
          itemQ1 = (Comparable) q1.front();
          itemQ2 = (Comparable) q2.front();
          if (itemQ1.compareTo(itemQ2) > 0) {   
            mergeQ.enqueue(q2.dequeue());
          } else if (itemQ1.compareTo(itemQ2) < 0) {
            mergeQ.enqueue(q1.dequeue());    
          } else {
            mergeQ.enqueue(q1.dequeue());
            mergeQ.enqueue(q2.dequeue());
          }
        }
        if (q1.isEmpty()) {                
          mergeQ.append(q2);
        } else {
          mergeQ.append(q1);               
        }
      } catch (QueueEmptyException e) {
      } 
    return mergeQ;
    }
  }


  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
     Comparable item;
     try {
      Comparable p = (Comparable) ((Edge)pivot).weight();
      while (qIn.size() > 0) {
        item = (Comparable) ((Edge)qIn.front()).weight();
        if (item.compareTo(p) > 0) {
          qLarge.enqueue(qIn.dequeue());
        } else if (item.compareTo(p) == 0) {
          qEquals.enqueue(qIn.dequeue());
        } else {
          qSmall.enqueue(qIn.dequeue());
        }
      }
    } catch (QueueEmptyException e) {
    } 
  }
  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    if (q.size() <= 1) {
      return;
    }
    LinkedQueue allQ = makeQueueOfQueues(q);
    LinkedQueue firstQ;
    LinkedQueue secondQ;
    LinkedQueue mergeQ;
    try {
      while (allQ.size() > 1) {
        firstQ = (LinkedQueue) allQ.dequeue();
        secondQ = (LinkedQueue) allQ.dequeue();
        mergeQ = mergeSortedQueues(firstQ, secondQ);
        allQ.enqueue(mergeQ);
      }
      q.append((LinkedQueue) allQ.dequeue());
    } catch (QueueEmptyException e) {
    }   
  }

 // I strongly advise that you also test that your mergesort and quicksort both
 // work on lists of size zero and one.  (Our autograder will.)

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if (q.size() <= 1) {
      return;
    }
    int pivot = (int) (q.size() * Math.random() + 1);
    LinkedQueue small = new LinkedQueue();
    LinkedQueue equals = new LinkedQueue();
    LinkedQueue large = new LinkedQueue();
    partition(q, (Comparable) q.nth(pivot), small, equals, large);
    quickSort(small);
    quickSort(large);
    q.append(small);
    q.append(equals);
    q.append(large);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  /* public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    //MY TEST
    LinkedQueue allQ = makeQueueOfQueues(q);
    System.out.println(allQ.toString());
    //Making sure queue is empty
    System.out.println("Should be true since it is empty: " + q.isEmpty());

    // Testing mergeSortedQueues.. 
    q = new LinkedQueue();
    LinkedQueue m = new LinkedQueue();
    q.enqueue(2);
    q.enqueue(4);
    q.enqueue(5);
    //m.enqueue(1);
    m.enqueue(3);
    m.enqueue(4);
    System.out.println(q.toString());
    System.out.println(m.toString());
    LinkedQueue mergeQ = mergeSortedQueues(q, m);
    System.out.println(mergeQ.toString());
    System.out.println(q.toString()); //just added
    System.out.println(m.toString()); //just added

    // Testing partition 
    //q.makeRandom(10);
    LinkedQueue s = new LinkedQueue();
    s.enqueue(2);
    s.enqueue(4);
    s.enqueue(5);
    s.enqueue(1);
    s.enqueue(3);
    s.enqueue(4);
    System.out.println(s.toString());
    LinkedQueue sma = new LinkedQueue();
    LinkedQueue e = new LinkedQueue();
    LinkedQueue l = new LinkedQueue();
    partition(s, 3, sma, e, l);
    System.out.println(s.toString());
    System.out.println(sma.toString());
    System.out.println(e.toString());
    System.out.println(l.toString());

    // Testing Mergesort on lists of size zero & one //
    
    // Test for zero
    LinkedQueue zero = new LinkedQueue();
    System.out.println(zero.toString());
    mergeSort(zero);
    System.out.println(zero.toString());

    // Test for size one
    q = makeRandom(1);
    System.out.println(q.toString());  
    mergeSort(q);
    System.out.println(q.toString());

    //just added line below //
    q = makeRandom(10);
    System.out.println(q.toString());  
    mergeSort(q);
    System.out.println(q.toString());
    
    // Testing QuickSort here //

    //Test for zero size
    LinkedQueue z = new LinkedQueue();
    System.out.println(z.toString());
    quickSort(z);
    System.out.println(z.toString());

    //Test for size one
    q = makeRandom(1);
    System.out.println(q.toString());  
    quickSort(q);
    System.out.println(q.toString());
    //
    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());


    //Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }  */
}

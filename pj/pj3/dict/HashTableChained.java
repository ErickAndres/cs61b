/* HashTableChained.java */

package dict;  //could get rid of this line
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  protected DList[] hashTable; 
  protected int size; 
  protected int buckets;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    buckets = closestPrime(sizeEstimate);
    hashTable = new DList[buckets];
    size = 0;
    for (int i = 0; i < buckets; i++) {
      hashTable[i] = new DList();
    }
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
    buckets = closestPrime(100);
    hashTable = new DList[buckets];
    size = 0;
    for (int i = 0; i < buckets; i++) {
      hashTable[i] = new DList();
    }
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return Math.abs((((600 *code + 99) % 67108865) % hashTable.length));
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }
  

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /* Resizes the hashTable, with double the number of elements */
  private HashTableChained resize() {
    HashTableChained newHash = new HashTableChained(buckets * 2);
    for (int i = 0; i < hashTable.length; i++) {
      try {
        if (hashTable[i] != null) {
          DListNode currNode = (DListNode) hashTable[i].front();
          while (currNode != null) {
            Entry item = (Entry) currNode.item();
            newHash.insert(item.key, item.value);
            currNode = (DListNode) currNode.next();
          }
        }
      } catch (InvalidNodeException e) {
      }
    }
    return newHash;
  }

  /* Returns the load factor of a hash table */   //for fixing
  public float loadFactor() {
    return (float) (size / buckets);
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    Entry current = new Entry();
    current.key = key;
    current.value = value;
    hashTable[index].insertFront(current);
    size++;
    return current;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int bucket = compFunction(key.hashCode());
    ListNode node = hashTable[bucket].front();
    while (node.isValidNode()) {
      try {
        Entry entry = (Entry) node.item();
        if (entry != null && key.equals(entry.key)) {
          return entry;
        }
        node = node.next();
      } catch (InvalidNodeException e) {
      }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    ListNode currNode = hashTable[index].front();
    while (currNode.isValidNode()) {
      try {
        Entry entry = (Entry) currNode.item();
		if (entry != null && key.equals(entry.key)) {
		  currNode.remove();
		  size--;
		  return entry;
		}
        currNode = currNode.next();
      } catch (InvalidNodeException e) {
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    size = 0;
    for (int i = 0; i < buckets; i++) {
      hashTable[i] = new DList();
    }
  }
  public static boolean isPrime(int n) {
      for (int divisor = 2; divisor < n; divisor++) {
        if (n % divisor == 0) {
          return false;
        }
      }
      return true;
  }
  public int closestPrime(int number) {
    if (isPrime(number)) {
      return number;
    } else {
      int nextNumber = number + 1;
      while (!(isPrime(nextNumber))) {
        nextNumber++;
      }
      return nextNumber;
    }
  }
  public int numOfCollisions() {
    int num = 0;
    for (int i = 0; i < hashTable.length; i++) {
      System.out.println(hashTable[i].length());
      if (hashTable[i].length() > 1) {
        num++;
      }
    }
    return num;
  }
}
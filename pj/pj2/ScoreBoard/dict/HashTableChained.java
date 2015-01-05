/* HashTableChained.java */

package ScoreBoard.dict;
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

  private DList[] hashTable;
  private int sizeOfTable;
  private int size;
  private int largePrime;
  private int a = 10, b = 10;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    int primeSize = findPrime(sizeEstimate * 2);
    hashTable = new DList[primeSize];
    for (int i = 0; i < primeSize; i++){
      hashTable[i] = new DList();
    }
    sizeOfTable = primeSize;
    size = 0;
    largePrime = findPrime((int)Math.pow(sizeOfTable, 2));
    //System.out.println("For my size estimate of " + sizeEstimate + ", the table is of size " + sizeOfTable + " and my largePrime is " + largePrime +"\n");

  }

  /**
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    hashTable = new DList[101];
    for (int i = 0; i < 101; i++){
      hashTable[i] = new DList();
    }
    sizeOfTable = 101;
    size = 0;
    largePrime = findPrime((int)Math.pow(sizeOfTable, 2));
    //System.out.println("My largePrime for a table of 101 is " + largePrime + "\n");
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int num = (((a * code) + b) % largePrime) % sizeOfTable;
    if (num < 0) {
      num += sizeOfTable;
    }
    return num;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
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
    Entry item = new Entry();
    item.key = key; 
    item.value = value;
    int code = key.hashCode(); 
    //System.out.println("This is the result of my hashCode: " + code);
    int entry = compFunction(code);
    //System.out.println("This is the result of my compFunction: " + entry + "\n");

    List list = hashTable[entry]; 
    list.insertBack(item);
    size++;
    return item; 
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
    Entry entry = iterate(key, "Find");
    return entry;
    
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
    Entry entry = iterate(key, "Remove");
    size--;
    return entry;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for (int i = 0; i < sizeOfTable; i++){
      hashTable[i] = new DList();
    }
    size = 0;
  }


  ///////////////////////
  // Helper Functions! //
  ///////////////////////

  /** 
   * This method uses the Sieve of Eratosthenes to find the 
   * closest prime number.
   */

  public int findPrime(int n){
    boolean[] prime = new boolean[n + 1];
    int i;
    for(i = 2; i <= n; i++) {
      prime[i] = true;
    }
    for(int divisor = 2; divisor * divisor <= n; divisor++){
      if(prime[divisor]) {
        for (i = 2 * divisor; i <= n; i = i + divisor) {
          prime[i] = false;
        }
      }
    }
    int primeNumber = 2;
    for (i = 2; i <= n; i++) {
      if (prime[i]) {
        primeNumber = i;
      }
    }
    return primeNumber;
  }

 /**
  * This method iterates through dictionary and performs the 
  * correct operation according to the "command".
  */

  private Entry iterate(Object key, String command){
    int code = key.hashCode();
    int num = compFunction(code);
    List list = hashTable[num];
    Entry entry = null;
    try{
      if (!list.isEmpty()) {
        ListNode curr = list.front();
        ListNode last = list.back();
        while (curr != last.next()){
          if (((Entry)curr.item()).key() == key) {
            entry = (Entry)curr.item();
            if (command == "Remove"){
              curr.remove();
            } 
          }
          curr = curr.next();
        }
      }
    }catch(InvalidNodeException e){
      return entry;
    }
    return entry;
  }

  public int numCollisions(){
    List list; 
    int total = 0;
    for (int i = 0; i < sizeOfTable; i++){
      list = hashTable[i];
      if (list.length() > 1){
        total += list.length();
        //System.out.println("There are " + list.length() + " collisions at element " + i + " in the array!");
      }
    }
    return total;
  }







}

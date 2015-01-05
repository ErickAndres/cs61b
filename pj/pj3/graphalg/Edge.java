/* Edge.java */
package graphalg;

public class Edge implements Comparable {

  protected Object v1;
  protected Object v2;
  protected int weight;

  public Edge(Object v1, Object v2, int w) {
  	this.v1 = v1;
  	this.v2 = v2;
  	this.weight = w;
  }
  
  public Object v1() {
    return v1;
  }

  public Object v2() {
    return v2;
  }

  public int weight() {
    return weight;
  }

  public void changeWeigth(int w) {
  	this.weight = w;
  }
  
  public int compareTo(Object obj) {
    if (weight() > ((Edge) obj).weight()) {
      return 1;
    } else if (weight() < ((Edge) obj).weight()) {
      return -1;
    } else {
      return 0;
    }
  }
  public String toString() {
    return "[ " + "v1" + ", " + "v2" + " ]" +  "and the weight is " + weight;
  }
}
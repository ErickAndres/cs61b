/* Edge.java */
package graph;
import list.*;

class Edge {
  protected Object v1;
  protected Object v2;
  protected int weight;
  protected DListNode myNode;
  protected DListNode partner;

  public Edge(Vertex v1, Vertex v2, int w) {
    if(v2 == null){
      myNode = v1.insertNeighbor(v1.object());
    }else{
      myNode = v1.insertNeighbor(v2.object());
      partner = v2.insertNeighbor(v1.object());
    }
    this.v1 = v1;
    this.v2 = v2;
    this.weight = w;
  }
  public int weight() {
  	return weight;
  }
  public void setWeight(int w) {
  	this.weight = w;
  }
  public String toString() {
  	return v1 + " " + v2 + ": " + weight;
  }
  public void removeEdges() {
    try {
      myNode.remove();
      if (v2 != null){
        partner.remove();
      }
    } catch (InvalidNodeException e) {
    }
  }
}
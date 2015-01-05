/* Kruskal.java */

package graphalg;

import graph.*;
import set.*; 
import dict.*;
import list.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
  	WUGraph t = new WUGraph();
    Object[] vertices = g.getVertices();
    for (int i = 0; i < vertices.length; i++) {
      t.addVertex(vertices[i]);
    }
    LinkedQueue edges = new LinkedQueue();
    Neighbors neighbor;
    Object[] nList;
    int[] weights;
    Edge e;
    for (int i = 0; i < vertices.length; i++) {
      Object vertex = vertices[i];
      neighbor = g.getNeighbors(vertex);
      nList = neighbor.neighborList;
      weights = neighbor.weightList;
      for (int j = 0; j < nList.length; j++) {
        e = new Edge(vertex, nList[j], weights[j]); 
        edges.enqueue(e);
      }
    }
    ListSorts.quickSort(edges);
    DisjointSets result = new DisjointSets(vertices.length); 
    HashTableChained table = new HashTableChained(vertices.length); 
    for (int i = 0; i < vertices.length; i++) {
      table.insert(vertices[i], i);                  
    }
    while (!edges.isEmpty()) { 
      try {
        Edge curr = (Edge) edges.dequeue();
        int v1 = (int) table.find(curr.v1()).value();
        int v2 = (int) table.find(curr.v2()).value();
        int v1root = result.find(v1);
        int v2root = result.find(v2);
        if (v1root != v2root) {
          t.addEdge(curr.v1(), curr.v2(), curr.weight());
          result.union(result.find(v1), result.find(v2));
        }
      } catch (QueueEmptyException exc) {
      }
    }
    return t;
  }
}

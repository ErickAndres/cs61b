/* WUGraph.java */

package graph;   //DOING ALL EDGES FUNCTIONS, including constructor-hav
import dict.*; //added this line
import list.*; //added this line
/** 
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  
  protected HashTableChained vertices, edges;  
  protected DList verticeList; 
  protected int numOfEdges, numOfVertices;  
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertices = new HashTableChained();
    edges = new HashTableChained();
    verticeList = new DList();
    numOfEdges = 0;
    numOfVertices = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return numOfVertices;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return numOfEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] objects = new Object[verticeList.length()];
    try {
      if (!verticeList.isEmpty()){
        ListNode curr = verticeList.front();
        int count = 0;
        while(curr != null){
          objects[count] = curr.item();
          curr = curr.next();
          count++;
        }
      }
    } catch (InvalidNodeException e) {
    }
    return objects;

  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    Entry v = vertices.find(vertex);
    if (v == null){
      verticeList.insertBack(vertex);
      Vertex vert = new Vertex((DListNode) verticeList.back());
      vertices.insert(vertex, vert);
      numOfVertices++;
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    Entry v = vertices.find(vertex);
    if (v != null){
      Vertex old = (Vertex) v.value();
      Neighbors neighbors = getNeighbors(old.object());
      if(neighbors != null){
        Object[] nList = neighbors.neighborList;
        for (int i = 0; i < nList.length; i++){
          removeEdge(vertex, nList[i]);
        }
      }
      old.remove();
      vertices.remove(vertex);
      numOfVertices--;
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    Entry v = vertices.find(vertex);
    if (v != null){
      return v.key().equals(vertex);
    }
    return false;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    if (isVertex(vertex)) {
      return ((Vertex) vertices.find(vertex).value()).degree(); 
    }
    return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    if (degree(vertex) == 0){
      return null;
    } else {
      Neighbors neighbors = new Neighbors();
      neighbors.neighborList = new Object[degree(vertex)];
      neighbors.weightList = new int[degree(vertex)];
      int count = 0;
      Entry v = vertices.find(vertex);
      Vertex vert = (Vertex)v.value();
      DList nb = vert.neighbors();
      DListNode curr = (DListNode)nb.front();
      try {
        while (curr != null) {
          neighbors.neighborList[count] = curr.item();
          neighbors.weightList[count] = weight(vertex, curr.item());
          count++;
          curr = (DListNode) curr.next();
        }
      } catch (InvalidNodeException e) {
      }
      return neighbors;
    }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    VertexPair myPair = new VertexPair(u, v);
    if (isEdge(u, v)) { 
      ((Edge) edges.find(myPair).value()).setWeight(weight); 
    } else {
      if (u.equals(v)) {  
        Vertex v1 = getVertex(u);
        Edge e = new Edge(v1, null, weight);
        edges.insert(myPair, e);
      } else { 
        Vertex v1 = getVertex(u);
        Vertex v2 = getVertex(v);
        Edge e = new Edge(v1, v2, weight);
        edges.insert(myPair, e);
      }
    numOfEdges++;  
    } 
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    if (isEdge(u, v)) {
      VertexPair currPair = new VertexPair(u, v);
      Edge uv = getEdge(currPair);
      uv.removeEdges();
      edges.remove(currPair);
      numOfEdges--;
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    if (isVertex(u) && isVertex(v)) {
      VertexPair pair = new VertexPair(u, v);
      if (getEdge(pair) != null) {
        return true;
      } 
    }
    return false;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    VertexPair pair = new VertexPair(u, v);
    Edge e = getEdge(pair);
    if (e != null) {
      return e.weight();
    }
    return 0;
  }
  /* Helper Functions */

  /*
   * getEdge() returns an internal implementation of an Edge
   * If the Edge is in the graph returns that implemmentation of that Edge
   * Otherwise, return null
   * @param VertexPair pair is the internal representation as a hashcode to a key in the hashTable
  */
  private Edge getEdge(VertexPair pair) {  
    Entry edgeEntry = edges.find(pair);
    if (edgeEntry != null) {
      return (Edge) edgeEntry.value();
    } 
    return null;
  }

  /**
   * getVertex() returns an internal implementation of an Object Vertex
   * If the vertex is in the graph returns that implemmentation of that Vertex
   * Otherwise, return null
   * @param Object Vertex which is an object that represents a Vertex inst.
   */
  private Vertex getVertex(Object vertex) {
    Entry vertexEntry = vertices.find(vertex);
    if (vertexEntry != null) {
      return (Vertex) vertexEntry.value();
    }
    return null;
  }
}

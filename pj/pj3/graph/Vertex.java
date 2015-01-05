package graph;
import list.*;

class Vertex{

	protected DListNode myNode;
	protected DList neighbors;
	
	public Vertex(DListNode node){
		myNode = node;
		neighbors = new DList();
	}

	public DListNode insertNeighbor(Object neighbor){
		neighbors.insertBack(neighbor);
		return (DListNode) neighbors.back();
	}

	public void remove() {
      try {
      	myNode.remove();
      } catch (InvalidNodeException e) {
      }
	}

	public DListNode node(){
		return myNode;
	}

	public Object object(){
	   Object item = null; 
	   try {
		item = myNode.item();
	   } catch (InvalidNodeException e) {
	   }
	   return item;
	}

	public int degree(){
		return neighbors.length();
	}

	public DList neighbors(){
		return neighbors;
	}
}
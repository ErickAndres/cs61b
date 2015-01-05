/* Wall.java */
import java.util.*;
import set.*;
// Class reprenting a wall //
public class Wall {
  private int x;
  private int y;
  private String type;  //if using string remember to use this.equals method;
  
  public final static String v = "Vertical";
  public final static String h = "Horizontal";

  public Wall(int x, int y, String type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }
  public String getType() {
  	return type;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }

}
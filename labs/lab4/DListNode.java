public class DListNode {

  public int pixNum;
  short r,g,b;
  public DListNode prev;
  public DListNode next;

  DListNode() {
    pixNum = 0;
    r = g = b = (short)0;
    prev = null;
    next = null;
  }

  DListNode(int i, short rgb) {
    pixNum = i;
    r = g = b = rgb;
    prev = null;
    next = null;
  }
  DListNode(int i, short red, short green, short blue)
  {
  pixNum = i;
  r = red;
  g = green;
  b = blue;
  prev = null;
  next = null;
  }
} 
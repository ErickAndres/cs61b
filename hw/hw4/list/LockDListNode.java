/* LockDListNode.java */
package list;

class LockDListNode extends DListNode {
	protected boolean locked;

	LockDListNode(Object item, DListNode prev, DListNode next) {
		super(item, prev, next);
		locked = false;
	}
}
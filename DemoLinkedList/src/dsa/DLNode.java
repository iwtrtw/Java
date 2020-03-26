package dsa;

/*
 * 基于位置接口实现的双向链表节点类
 */

public class DLNode implements Position{
	//数据对象
	private Object element;
	
	//指向前驱节点
	private DLNode prev;
	
	//指向后继节点
	private DLNode next;
	
	
	public DLNode() {
		this(null,null,null);
	}
	
	public DLNode(Object e,DLNode p,DLNode n) {
		element =e;
		prev = p;
		next =n;
	}
	
	@Override
	public Object getElem() {
		return element;
	}

	//将给定元素存放至该位置，返回此前存放的元素
	@Override
	public Object setElem(Object e) {
		Object oldElem = element;
		element =e;
		return oldElem;
	}
	
	//找到后继位置
	public DLNode getNext() {
		return next;
	}
	
	//找到前驱位置
	public DLNode getPrev() {
		return prev;
	}
	
	//修改后继位置
	public void setNext(DLNode newNext) {
		next = newNext;
	}
	
	//修改前驱位置
	public void setPrev(DLNode newPrev) {
		prev = newPrev;
	}

}

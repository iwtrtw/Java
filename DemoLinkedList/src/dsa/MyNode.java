package dsa;

import javax.xml.soap.Node;

public class MyNode implements Position{

	//数据对象
	private Object element;
	
	//指向后继节点
	private MyNode next ;
	
	public MyNode() {
		//指定数据对象、后继节点的引用都置空
		this(null,null);
	}
	
	public MyNode(Object e,MyNode n) {
		//指定数据对象及后继节点
		element =e;
		next = n;
	}
	
	//返回存放于该位置的元素
	@Override
	public Object getElem() {
		return element;
	}

	//将给定元素存放至该位置，返回此前存放的元素
	@Override
	public Object setElem(Object e) {
		Object oldElem = element;
		element = e;
		return oldElem;
	}
	
	//获取当前节点的后继节点
	public MyNode getNext() {
		return next;
	}
	
	
	//修改当前节点的后继节点
	public void setNext(MyNode newNext) {
		next = newNext;
	}

}

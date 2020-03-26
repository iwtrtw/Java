package dsa;
/*
 * 基于链表实现栈结构
 */

public class Stack_List implements MyStack{
	
	//指向栈顶元素
	protected MyNode top;
	
	//栈中元素的数目
	protected int size;
	
	//空栈
	public Stack_List() {
		top = null;
		size = 0;
	}

	//获取当前栈的规模
	@Override
	public int getSize() {
		return size;
	}

	//判断栈是否为空
	@Override
	public boolean isEmpty() {
		if(top==null) {
			return true;
		}else{
			return false;
		}
	}

	//获取栈顶元素
	@Override
	public Object top() throws ExceptionStackEmpty {
		if(isEmpty()) {
			throw new ExceptionStackEmpty("异常：栈空");
		}
		return top.getElem();
	}

	//压栈
	@Override
	public void push(Object ele) {
		//创建新节点作为首节点插入
		MyNode newN=new MyNode(ele,top);
		top = newN;
		size++;
	}

	//出栈
	@Override
	public Object pop() throws ExceptionStackEmpty {
		if(isEmpty()) {
			throw new ExceptionStackEmpty("异常：栈空");
		}
		Object temp = top.getElem();
		top = top.getNext();
		size--;
		return temp;
	}

}

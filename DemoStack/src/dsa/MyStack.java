package dsa;

/*
 * 栈接口
 */
public interface MyStack {
	
	//返回栈中元素数目
	public int getSize();
	
	//判断栈是否为空
	public boolean isEmpty();
	
	//取栈顶元素（但不删除）
	public Object top() throws ExceptionStackEmpty;
	
	//入栈
	public void push(Object ele);
	
	//出栈
	public Object pop() throws ExceptionStackEmpty;

}

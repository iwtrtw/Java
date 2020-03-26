package dsa;

/*
 * 队列接口
 */

public interface MyQueue {
	//返回队列中元素数目
	public int getSize();
	
	//判断队列是否为空
	public boolean isEmpty();
	
	//取队首元素（不删除）
	public Object front();
	
	//入队
	public void enqueue(Object obj);
	
	//出队
	public Object dequeue();

}

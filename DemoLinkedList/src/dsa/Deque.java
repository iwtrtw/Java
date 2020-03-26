package dsa;
/*
 * 双端队列接口
 */

public interface Deque {
	//返回队列中元素数目
	public int getSize();
	
	//判断队列是否空
	public boolean isEmpty();
	
	//取首元素
	public Object first() throws ExceptionQueueEmpty;
	
	//取末元素
	public Object last() throws ExceptionQueueEmpty;
	
	//入队（首元素）
	public void insertFirst(Object obj);
	
	//入队（末元素）
	public void insertLast(Object obj);
	
	//出队（首元素）
	public Object removeFirst() throws ExceptionQueueEmpty;
	
	
	//出队（末元素）
	public Object removeLast() throws ExceptionQueueEmpty;
	
	//遍历
	public void traversal();
	

}

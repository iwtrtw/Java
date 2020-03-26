package dsa;
/*
 * 基于单链表实现队列结构
 */

public class Queue_List implements MyQueue{
	
	//指向表首元素
	protected MyNode head;
	
	//指向表末元素
	protected MyNode tail;
	
	//队列中元素的数目
	protected int size;
	
	//空队列
	public Queue_List() {
		head = tail =null;
		size = 0;
	}
	

	//查询当前队列的规模
	@Override
	public int getSize() {
		
		return size;
	}

	//判断队列是否为空
	@Override
	public boolean isEmpty() {
		if(size ==0) {
			return true;
		}else {
			return false;
		}
	}

	//获取栈首元素
	@Override
	public Object front() throws ExceptionQueueEmpty{
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}else {
			return head.getElem();
		}
	}

	//入队
	@Override
	public void enqueue(Object obj) {
		MyNode newN = new MyNode();
		newN.setElem(obj);
		newN.setNext(null);
		
		if(size==0) {
			//当前队列为空,直接插入
			head = newN;
		}
		else {
			//新节点在队列末端插入
			tail.setNext(newN);
		}
		tail = newN;
		size++;
	}

	//出队
	@Override
	public Object dequeue() throws ExceptionQueueEmpty {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}else {
			Object object = head.getElem();
			head = head.getNext();
			size--;
			if(size==0) {
				tail=null;
			}
			return object;
			
		}
	}

}

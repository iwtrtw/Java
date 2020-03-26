package dsa;

/*
 * 借助定长循环数组实现Queue接口
 */

public class Queue_Array implements MyQueue{
	
	//数组的默认容量
	public static final int CAPACITY=1024;
	
	//数组实际容量
	protected int capacity;
	
	//对象数组
	protected Object[] qA;
	
	//队首元素位置
	protected int f = 0;

	//队尾元素位置(下一次入队元素的位置)
	protected int r =0;
	
	public Queue_Array() {
		this(CAPACITY);
	}
	
	//按指定容量创建对象
	public Queue_Array(int cap) {
		capacity =cap;
		qA =  new Object[capacity]; 
	}
	
	
	//获取当前队列的规模
	@Override
	public int getSize() {
		return (capacity+r-f)%capacity;
	}

	//判断队列是否为空
	@Override
	public boolean isEmpty() {
		//队满时也是f==r，为此需限制队列的实际规模N-1
		return (f==r);
	}

	//取队首元素（不删除）
	@Override
	public Object front() {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}
		else {
			
		
			return qA[f];
		}
	}

	//入队
	@Override
	public void enqueue(Object obj) {
		if(getSize() == capacity-1) {
			throw new ExceptionQueueFull("异常:队列满了");
		}else {
			qA[r] = obj;
			r = (r+1)%capacity;
		}
		
	}

	//出队
	@Override
	public Object dequeue() {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}
		else {	Object elem = qA[f];
		qA[f] = null;
		f=(f+1)%capacity;
		return elem;
		}
		
	}
	
	
	//遍历
	public void traversal() {
		for(int i=f;i<r;i++) {
			System.out.print(qA[i]+" ");
		}
		System.out.println();
	}

}

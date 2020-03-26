package dsa;

/*
 *基于双向列表实现双端队列结构 
 */

public class Deque_DLNode implements Deque{
	//指向头节点
	private DLNode header;
	
	//指向尾结点
	private DLNode trailer;
	
	//队列中元素的数目
	protected int size;
	
	public Deque_DLNode() {
		header = new DLNode();
		trailer = new DLNode();
		header.setNext(trailer);
		trailer.setPrev(header);
		size = 0;
	}

	//返回队列中元素数目
	@Override
	public int getSize() {
		return size;	
	}

	//判断队列是否为空
	@Override
	public boolean isEmpty() {
		return (0 == size) ?true:false;
	}

	//取队首元素（不删除）
	@Override
	public Object first() throws ExceptionQueueEmpty {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}else {
			
			return header.getNext().getElem();
		}
	
	}

	//取末元素（不删除）
	@Override
	public Object last() throws ExceptionQueueEmpty {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}else {
			return trailer.getPrev().getElem();
		}
	}

	//在队列前端插入新节点
	@Override
	public void insertFirst(Object obj) {
		DLNode second = header.getNext();
		DLNode newDlNode = new DLNode(obj,header,second);
		second.setPrev(newDlNode);
		header.setNext(newDlNode);
		size++;
	}

	//在队列后端插入新节点
	@Override
	public void insertLast(Object obj) {
		DLNode lastSecond = trailer.getPrev();
		DLNode newDlNode = new DLNode(obj,lastSecond,trailer);
		lastSecond.setNext(newDlNode);
		trailer.setPrev(newDlNode);
		size++;
	}

	//删除首节点
	@Override
	public Object removeFirst() throws ExceptionQueueEmpty {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：队列为空");
		}else {
			DLNode toRemove = header.getNext();
			DLNode toFirst = toRemove.getNext();
			header.setNext(toFirst);
			toFirst.setPrev(header);
			size-- ;
			return(toRemove.getElem());
		}
	}

	//删除末节点
	@Override
	public Object removeLast() throws ExceptionQueueEmpty {
		if(isEmpty()) {
			throw new ExceptionQueueEmpty("异常：空队列");
		}else {
			DLNode toRmove = trailer.getPrev();
			DLNode last = toRmove.getPrev();
			trailer.setPrev(last);
			last.setNext(trailer);
			size --;
			return(toRmove.getElem());
		}
	}

	//遍历
	@Override
	public void traversal() {
		DLNode dNode =header.getNext();
		while (dNode != trailer) {
			System.out.println(dNode.getElem()+" ");
			dNode = dNode.getNext();
		}
		System.out.println();
		
	}

}

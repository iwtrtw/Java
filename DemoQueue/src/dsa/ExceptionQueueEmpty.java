package dsa;
/*
 * 当队列为空时取队首元素时抛出该异常
 */
public class ExceptionQueueEmpty extends RuntimeException{

	public ExceptionQueueEmpty(String err) {
		super(err);
	}
}

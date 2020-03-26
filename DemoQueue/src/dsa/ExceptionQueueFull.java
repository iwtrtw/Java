package dsa;
/*
 * 当队列满仍入队时抛出该异常
 */

public class ExceptionQueueFull extends RuntimeException{

	public ExceptionQueueFull(String err) {
		super(err);
	}
}

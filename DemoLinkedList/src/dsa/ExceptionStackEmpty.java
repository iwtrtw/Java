package dsa;

/*
 * 对空栈使用pop或top方法是，抛出异常
 */


public class ExceptionStackEmpty extends RuntimeException{
	public ExceptionStackEmpty(String err) {
		super(err);
	}

}

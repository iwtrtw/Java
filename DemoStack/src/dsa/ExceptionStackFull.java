package dsa;

/*
 * 当栈满时依旧push操作，抛出异常(使用数组实现栈)
 */

public class ExceptionStackFull extends RuntimeException{
	public ExceptionStackFull (String err) {
		super(err);
	}

}

package dsa;

/*
 * 当作为参数的数组下标、向量的秩或列表的位置越界时，则抛出该异常
 */

public class ExceptionBoundaryViolation extends RuntimeException{
	public ExceptionBoundaryViolation(String err) {
		super(err);
	}

}

package dsa;

import javax.swing.text.TabStop;

/*
 * 使用定长数组实现栈
 */

public class Stack_Array implements MyStack {

	// 栈顶元素的位置
	protected int top = -1;

	// 数组的实际容量
	protected int capacity;

	// 数组的默认容量
	public static final int CAPACITY = 1024;

	// 对象数组
	protected Object[] sA;

	// 按默认容量创建对象
	public Stack_Array() {
		this(CAPACITY);
	}

	// 按指定容量创建对象
	public Stack_Array(int cap) {
		capacity = cap;
		sA = new Object[capacity];
	}

	// 获取栈当前的大小
	@Override
	public int getSize() {
		return (top + 1);
	}

	// 判断栈是否为空
	@Override
	public boolean isEmpty() {
		if (top == -1) {
			return true;
		} else {
			return false;
		}
	}

	// 取栈顶元素
	@Override
	public Object top() throws ExceptionStackEmpty {
		if (isEmpty()) {
			throw new ExceptionStackEmpty("异常：栈为空");
		} else {
			return sA[top];
		}
	}

	// 入栈
	@Override
	public void push(Object ele) {
		if (getSize() == capacity) {
			throw new ExceptionStackFull("异常：栈溢出");
		} else {
			sA[++top] = ele;
		}

	}

	//出栈
	@Override
	public Object pop() throws ExceptionStackEmpty {
		Object elem;
		if (isEmpty()) {
			throw new ExceptionStackEmpty("异常：栈空");
		}
		elem = sA[top];
		sA[top--] = null;
		return elem;
	}

}

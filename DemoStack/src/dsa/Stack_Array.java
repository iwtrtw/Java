package dsa;

import javax.swing.text.TabStop;

/*
 * ʹ�ö�������ʵ��ջ
 */

public class Stack_Array implements MyStack {

	// ջ��Ԫ�ص�λ��
	protected int top = -1;

	// �����ʵ������
	protected int capacity;

	// �����Ĭ������
	public static final int CAPACITY = 1024;

	// ��������
	protected Object[] sA;

	// ��Ĭ��������������
	public Stack_Array() {
		this(CAPACITY);
	}

	// ��ָ��������������
	public Stack_Array(int cap) {
		capacity = cap;
		sA = new Object[capacity];
	}

	// ��ȡջ��ǰ�Ĵ�С
	@Override
	public int getSize() {
		return (top + 1);
	}

	// �ж�ջ�Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		if (top == -1) {
			return true;
		} else {
			return false;
		}
	}

	// ȡջ��Ԫ��
	@Override
	public Object top() throws ExceptionStackEmpty {
		if (isEmpty()) {
			throw new ExceptionStackEmpty("�쳣��ջΪ��");
		} else {
			return sA[top];
		}
	}

	// ��ջ
	@Override
	public void push(Object ele) {
		if (getSize() == capacity) {
			throw new ExceptionStackFull("�쳣��ջ���");
		} else {
			sA[++top] = ele;
		}

	}

	//��ջ
	@Override
	public Object pop() throws ExceptionStackEmpty {
		Object elem;
		if (isEmpty()) {
			throw new ExceptionStackEmpty("�쳣��ջ��");
		}
		elem = sA[top];
		sA[top--] = null;
		return elem;
	}

}

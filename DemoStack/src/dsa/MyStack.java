package dsa;

/*
 * ջ�ӿ�
 */
public interface MyStack {
	
	//����ջ��Ԫ����Ŀ
	public int getSize();
	
	//�ж�ջ�Ƿ�Ϊ��
	public boolean isEmpty();
	
	//ȡջ��Ԫ�أ�����ɾ����
	public Object top() throws ExceptionStackEmpty;
	
	//��ջ
	public void push(Object ele);
	
	//��ջ
	public Object pop() throws ExceptionStackEmpty;

}

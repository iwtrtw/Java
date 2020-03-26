package dsa;

public interface Position {
	
	//返回存放于该位置的元素
	public Object getElem();
	
	
	//将给定的元素放至该位置，返回此前存放的元素
	public Object setElem(Object e);
	

}

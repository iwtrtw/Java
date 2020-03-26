package dsa;

/*
 * Josephus环
 */

public class Josephus {

	
	//初始化队列
	public static Queue_Array buildQueue(Object a[]) {
		Queue_Array  Q = new Queue_Array();
		for(int i =0;i<a.length;i++) {
			Q.enqueue(a[i]);
		}
//		Q.traversal();
		return Q;
	}
	
	
	//
	public static Object Josephus(Queue_Array Q , int k) {
		if(Q.isEmpty()) {
			return null;
		}
		while (Q.getSize()>1) {
			Q.traversal();
			for(int i= 0;i<k-1;i++) {
				Q.enqueue(Q.dequeue());
			}
			//拿着山芋的孩子退出
			Object e = Q.dequeue();
			System.out.println("\n\t"+e+"退出");
		}
		return Q.dequeue();
	
	}
	
	public static void main(String[] args) {
		String[] kidStrings = {
				"Alice","Bob","Cindy","Doug","Fred",
				"Ed","Gene","Hope","Irene","Kim","Lance",
				"Mike","Nancy","Jack","Ollie"
		};
		
		System.out.println("最终的幸运者时"+Josephus(buildQueue(kidStrings),6));
	}
	

}

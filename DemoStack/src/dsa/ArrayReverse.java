package dsa;
/*
 * Êı×éµ¹ÖÃ
 */

public class ArrayReverse{
	
	
	public Integer[] reverseByStack(Integer[] a) {
		Stack_Array sA = new Stack_Array(a.length) ;
		Integer[] b = new Integer[a.length];
		for (int i=0;i<a.length;i++) {
			sA.push(a[i]);
		}
		for(int i =0;i<a.length;i++) {
			b[i] = (Integer) sA.pop();
		}
		return b;	
	}
	
	public void reverseArray(Integer[] a,int left,int right) {
		if(left<right) {
			Integer temp = a[left];
			a[left] = a[right];
			a[right] = temp;
			reverseArray(a,left+1,right-1);
		}
	}
	

}

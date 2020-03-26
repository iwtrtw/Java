package cn.iwtrtw.sort;
/*
选择排序
 */

public class Selection implements Sort{

    //对数组a中的元素进行选择排序
    public  void sort(Comparable[] a) {

        for(int i=0;i<a.length-1;i++){
            int minIndex = i;
            for(int j=i+1;j<a.length;j++){
                if(greater(a[minIndex],a[j])) {
                    minIndex = j;
                }
            }
            exchnge(a,i,minIndex);
        }
    }

    //比较v元素是否大于w
    public  boolean greater(Comparable v, Comparable w) {
//        int result = v.compareTo(w);
//        if (result > 0) {
//            return true;
//        } else
//            return false;

        return v.compareTo(w)>0;
    }

    //交换i和j的位置
    public void exchnge(Comparable[] a, int i, int j) {
        Comparable temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

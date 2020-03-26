package cn.iwtrtw.sort;

/*
冒泡排序
 */

public class Bubble implements Sort{

    //对数组a中中的元素进行冒泡排序
    public void sort(Comparable[] a) {
        for(int i = a.length-1;i>0;i--){
            for(int j=0;j<i;j++){
                if (greater(a[j],a[j+1])){
                    exchnge(a,j,j+1);
                }
            }
        }

    }

    //比较v元素是否大于w
    public boolean greater(Comparable v, Comparable w) {
        int result = v.compareTo(w);
        if (result > 0) {
            return true;
        } else
            return false;

//        return v.compareTo(w)>0
    }

    //交换i和j的位置
    public void exchnge(Comparable[] a, int i, int j) {
        Comparable temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

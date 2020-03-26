package cn.iwtrtw.sort;

public interface Sort {

    //对数组a中中的元素进行冒泡排序
    public void sort(Comparable[] a) ;

    //比较v元素是否大于w
   public boolean greater(Comparable v, Comparable w);

    //交换i和j的位置
    public void exchnge(Comparable[] a, int i, int j);
}

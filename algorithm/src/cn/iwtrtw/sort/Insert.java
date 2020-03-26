package cn.iwtrtw.sort;

/*
插入排序
 */
public class Insert implements Sort{

    //插入排序
    public void sort(Comparable[] a) {
        //待排序i
        for(int i=1;i<a.length;i++){
            for(int j =i;j>0;j--){
                if(greater(a[j-1],a[j])){
                    exchnge(a,j-1,j);
                }else {
                    break;
                }
            }
        }


    }

    //比较v元素是否大于w
    public boolean greater(Comparable v, Comparable w) {
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

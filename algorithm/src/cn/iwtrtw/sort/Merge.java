package cn.iwtrtw.sort;

/*
归并排序
 */

public class Merge implements Sort{

    //归并所需要的辅助数组
    private Comparable[] assist;

    /*
    对数组a中的元素进行排序
     */
    @Override
    public void sort(Comparable[] a) {
        assist  =new Comparable[a.length];
        int left =0;
        int right = a.length-1;
        mergeSort(a,left,right);
    }

    public void mergeSort(Comparable[] a,int left,int right){
        if(right<=left) {
            return;
        }

        int mid = left+(right-left)/2;

        mergeSort(a,left,mid);
        mergeSort(a,mid+1,right);
        merge(a,left,mid,right);

    }

    public void merge(Comparable[] a,int left,int mid,int right){

        int i = left;
        int p1=left;
        int p2 =mid+1;

        while (p1<=mid && p2<=right){
            if(greater(a[p1],a[p2])){
                assist[i++]=a[p1++];
            }else {
                assist[i++] = a[p2++];
            }
        }

        while (p1<=mid){
            assist[i++] =a[p1++];
        }

        while (p2<=right){
            assist[i++]=a[p2++];
        }

        for (int index =left;index<=right;index++){
            a[index] =assist[index];
        }
    }

    @Override
    public boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w)<0;
    }

    @Override
    public void exchnge(Comparable[] a, int i, int j) {
        Comparable temp =a[i];
        a[i] = a[j];
        a[j] =temp;
    }
}

package cn.iwtrtw.sort;

public class Quick implements Sort{


    @Override
    public void sort(Comparable[] a) {
        int left=0;
        int right = a.length-1;
        quicksort(a,left,right);

    }

    public void quicksort(Comparable[] a,int left, int right){
        //安全性校验(递归停止条件)
        if(right<=left){
            return;
        }
        int partition=partition(a,left,right);
        quicksort(a,left,partition);
        quicksort(a,partition+1,right);
    }

    public int partition(Comparable[] a,int left,int right){
        //确定分界值
        Comparable key = a[left];
        //定义两个指针
        int low =left;
        int high =right+1;//指向空

        //切分
        while (true){
            //从左往右扫描，移动high指针，找到比分界值小的元素
            while (greater(key,a[--high])){
                if(low==high){
                    break;
                }
            }

            //从右往左扫描，移动low指针，找到比分界值大的元素
            while (greater(a[++low],key)){
                if(low==high){
                    break;
                }
            }
            //如果left>=right,则扫描结束，否则交换元素
            if(low>=high){
                break;
            }else {
                exchnge(a,low,high);
            }
        }
        exchnge(a,left,high);
        return high;
    }

    @Override
    public boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w)<0;
    }

    @Override
    public void exchnge(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j]=temp;

    }
}

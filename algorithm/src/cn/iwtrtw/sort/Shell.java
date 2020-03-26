package cn.iwtrtw.sort;

/*
希尔排序
 */
public class Shell implements Sort{

    //希尔排序
    public void sort(Comparable[] a) {
//       根据数组a的长度，确定增长量h的值
        int h = 1;
        while (h < a.length / 2) {
            h = 2 * h + 1;
        }

        while (h >= 1) {
            //排序
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h; j = j - h) {
                    if (greater(a[j - h], a[j])) {
                        exchnge(a, j - h, j);
                    } else {
                        break;
                    }
                }
            }

            h = h/2;
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

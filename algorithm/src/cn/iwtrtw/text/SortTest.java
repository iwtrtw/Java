package cn.iwtrtw.text;

import cn.iwtrtw.sort.*;
import org.junit.Test;

public class SortTest {

    //冒泡排序(N-1+1)(N-1)/2+(N-1+1)(N-1)/2 =N^2 - N
    @Test
    public void  bubbleSort(){
        Integer[] arr = {4,5,6,3,2,1};
        Bubble bubble = new Bubble();
        bubble.sort(arr);
        for (Integer a:arr
             ) {
            System.out.print(a+" ");
        }
    }


    //选择排序
    @Test
    public void selectSort(){
        Integer[] arr = {4,5,6,8,9,2,10,1};
        Selection selection = new Selection();
        selection.sort(arr);
        for (Integer a:arr
                ) {
            System.out.print(a+" ");
        }
    }


    //插入排序
    @Test
    public void insertSort(){
        Integer[] arr = {4,3,2,10,12,1,5,6};
        Insert insert = new Insert();
        insert.sort(arr);
        for (Integer a:arr
                ) {
            System.out.print(a+" ");
        }
    }

    //插入排序
    @Test
    public void shellSort(){
        Integer[] arr = {9,1,2,5,7,8,6,3,5};
        Shell shell = new Shell();
        shell.sort(arr);
        for (Integer a:arr
                ) {
            System.out.print(a+" ");
        }
    }


    @Test
    public  void  compareTime(){
        Integer[] integers = SortCompare.randomGenrate(100000);
        Integer[] cloneInte = integers.clone();
        Insert insert = new Insert();
        Shell shell = new Shell();
        System.out.println("希尔排序"+SortCompare.testSortTime(shell,cloneInte));
        System.out.println("插入排序"+SortCompare.testSortTime(insert,integers));
    }

    //插入排序
    @Test
    public void mergeSort(){
        Integer[] arr = {8,4,5,7,1,3,6,2};
        Merge merge = new Merge();
        merge.sort(arr);
        for (Integer a:arr
                ) {
            System.out.print(a+" ");
        }
    }

    //快速排序
    @Test
    public void quickSort(){
        Integer[] arr = {6,1,2,7,9,3,4,5,8};
        Quick quick = new Quick();
        quick.sort(arr);
        for (Integer a:arr
                ) {
            System.out.print(a+" ");
        }
    }
}

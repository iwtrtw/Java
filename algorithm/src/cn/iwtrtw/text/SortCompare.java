package cn.iwtrtw.text;

import cn.iwtrtw.sort.Sort;

import java.util.ArrayList;
import java.util.Random;

public class SortCompare {

    public static Integer[] randomGenrate(int n){

        ArrayList<Integer> list = new ArrayList<>();
        Random rd = new Random();
        for(int i=0;i<n;i++){
            int temp =rd.nextInt(100000);
            list.add(temp);
        }
        Integer[] a = new Integer[list.size()];
        list.toArray(a);
        return a;

    }


    public static long testSortTime(Sort sort,Integer[] a){
        long startTime = System.currentTimeMillis();
        sort.sort(a);
        long endTime =System.currentTimeMillis();
        return (endTime-startTime);
    }










}

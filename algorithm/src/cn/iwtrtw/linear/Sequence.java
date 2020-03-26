package cn.iwtrtw.linear;

public class Sequence<T> {

    //存储元素的数组
    private T[] arry;

    //当前顺序表中元素的个数
    private int N;

    //构造方法
    public Sequence(int capacity){
        this.arry = (T[])new Object[capacity];
        this.N=0;
    }

    //置空线性表
    public void clear(){
        arry = null;
        this.N=0;
    }

    //判断当前线性表是否为空
    public boolean isEmpty(){
        return N==0;
    }

    //获取线性表的长度
    public int length(){
        return N;
    }

    //获取指定位置的元素
    public T get(int i){
        return arry[i];
    }

    //插入元素
    public void insert(T t){
        arry[N++]=t;
    }

    //指定位置插入元素
    public void insert(int i,T value){
        for(int j=N;j>i;j--){
            arry[N]=arry[N-1];
        }
        arry[i] = value;
        N++;
    }

    //删除指定位置的元素
    public T remove(int i){
        T temp = arry[i];
        for (int j =i;j<N-1;j++){
            arry[j]=arry[j+1];
        }
        N--;
        return temp;
    }

    //查找t元素第一次出现的位置
    public int indexOf(T t){
        for(int i=0;i<N;i++){
            if(arry[i]==t){
                return i;
            }
        }
        return -1;
    }






}

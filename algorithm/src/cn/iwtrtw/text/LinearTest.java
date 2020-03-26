package cn.iwtrtw.text;

import cn.iwtrtw.linear.Sequence;
import org.junit.Test;

public class LinearTest {

    @Test
    public void sequenceTest(){
        Sequence<Integer> sequence = new Sequence<>(10);
        sequence.insert(2);
        sequence.insert(4);
        sequence.insert(6);
        sequence.insert(2,8);

        Integer getThrid = sequence.get(2);
        System.out.println(getThrid);
        Integer removeResult =sequence.remove(2);

        System.out.println(removeResult);
        sequence.clear();
        System.out.println("清空数组 length"+sequence.length());
    }
}

package group.rober.runtime.kit;

import org.junit.Test;

public class DateKitTest {
    @Test
    public void test1(){
        System.out.println(DateKit.format(DateKit.parse("2018/03/03")));
        System.out.println(DateKit.format(DateKit.parse("2018//03--01")));
        System.out.println(DateKit.format(DateKit.parse("699379200000")));
    }
}

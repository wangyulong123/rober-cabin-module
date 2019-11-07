package group.rober.runtime.kit;

import org.junit.Test;

import java.util.Date;

public class NumberKitTest {

    @Test
    public void test36Radix(){
        System.out.println(NumberKit.convert36Radix(2017));
        System.out.println(NumberKit.convert36Radix(17));
        System.out.println(NumberKit.convert36Radix(37));
        System.out.println(NumberKit.convert36Radix(8));
        System.out.println(NumberKit.convert36Radix(19));
        System.out.println(NumberKit.convert36Radix(1708));
        System.out.println(NumberKit.convert36Radix(201708));
        System.out.println(NumberKit.convert36Radix(20170819));
    }

    @Test
    public void testNanoTime(){
        Long nano = System.nanoTime();
        Long millis = nano / 1000000;
        System.out.println(nano);
        System.out.println(System.currentTimeMillis());
        System.out.println(millis);
//        Date date = DateKit.parse(System.currentTimeMillis());
        Date date = DateKit.parse(millis);
//        Date date = DateKit.now();
        System.out.println(DateKit.getYear(date));

//        Long minutes = seconds / 60;
//        Long hours = minutes / 60;
//        Long days = hours / 24;
//        Long months = days/12;
//        Long years = months/12;
    }

    @Test
    public void testRoundX(){
        System.out.println(NumberKit.round(123.45658,3));
        System.out.println(NumberKit.round(123.45638,3));
        System.out.println(NumberKit.ceil(123.45638,3));
        System.out.println(NumberKit.floor(123.45638,3));
        System.out.println(NumberKit.ceil(123.45658,3));
        System.out.println(NumberKit.floor(123.45658,3));
        System.out.println(NumberKit.ceil(123.45658,0));
        System.out.println(NumberKit.floor(123.45658,0));
        System.out.println(NumberKit.ceil(10/3,0));
    }
}

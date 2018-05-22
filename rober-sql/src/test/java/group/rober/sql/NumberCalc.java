package group.rober.sql;

import group.rober.runtime.kit.NumberKit;
import org.junit.Test;

public class NumberCalc {

    @Test
    public void test01(){
        Integer totalCount = 101;
        Integer pageSize = 25;
        System.out.println(totalCount/pageSize);
//        System.out.println(NumberKit.divide(totalRowCount,pageSize).intValue());
        int pageCount = NumberKit.ceil(NumberKit.divide(totalCount,pageSize),0).intValue();
        System.out.println(pageCount);

    }
}

package group.rober.sql.kit;

import group.rober.runtime.kit.SQLKit;
import org.junit.Test;

public class SQLKitTest {
    @Test
    public void test1(){
        String[] datas = new String[]{
                "CustName",
                "CUST_NAME",
                "_NAME",
                "NAME_",
                "name",
                "''",
                "'10'",
                "null",
                "Null",
                "NULL",
                "'A10'",
                "10A",
                "A10",
        };
        for(String s:datas){
            System.out.println(s+"->"+SQLKit.isConstColumn(s));
        }
    }
}

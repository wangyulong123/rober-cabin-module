package group.rober.sql.parser;

import group.rober.runtime.lang.PairBond;
import group.rober.sql.dialect.SqlDialectType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SqlUpdateParserTest {

    @Test
    public void testUpdate(){
        String sql = "UPDATE DEMO_PERSON SET CODE=:code,NAME=:name,BIRTH=:birth WHERE  ID=:id and CODE=:code and NAME=:name AND Birth between :a and :b";
        SqlUpdateParser parser = new SqlUpdateParser(sql, SqlDialectType.MYSQL);
        parser.parse();

        List<PairBond<String, String[]>> fields = parser.getFields();
        for(PairBond<String, String[]> item : fields){
            System.out.println(item.getLeft()+"="+ Arrays.toString(item.getRight()));
        }
    }

    @Test
    public void testInsert(){
        String sql = "insert into DEMO_PERSON(CODE,NAME,BIRTH) values(:code,:name,:birth)";
        SqlInsertParser parser = new SqlInsertParser(sql, SqlDialectType.MYSQL);
        parser.parse();

        List<PairBond<String, String>> fields = parser.getFields();
        for(PairBond<String, String> item : fields){
            System.out.println(item.getLeft()+"="+ item.getRight());
        }
    }
}

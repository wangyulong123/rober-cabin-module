package group.rober.runtime.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MySqlInsertBenchmark {
    static String sql = "INSERT INTO test_table VALUES (1, '1', '2017-10-10', true, false, '2017-10-10 10:10:10', '10:10:10', 1.111, null);";
    List<SQLStatement> stmtList;

    @Before
    public void setUp() throws Exception {
        stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

    }

    public void test_perf() throws Exception {
        for (int i = 0; i < 5; ++i) {
            perf();
//            perf_toString(); // 425
        }
    }

    @Test
    public void perf_toString() {
        long startMillis = System.currentTimeMillis();
//        for (int i = 0; i < 1000 * 1000; ++i) {
        for (int i = 0; i < 1; ++i) {
            String sql = SQLUtils.toMySqlString(stmtList.get(0));
            System.out.println(sql);
        }
        long millis = System.currentTimeMillis() - startMillis;
        System.out.println("millis : " + millis);
    }

    public void perf() {
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000 * 1000; ++i) {

            List<SQLStatement> stmt = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
//            stmt.toString();
        }
        long millis = System.currentTimeMillis() - startMillis;
        System.out.println("millis : " + millis);
    }
}

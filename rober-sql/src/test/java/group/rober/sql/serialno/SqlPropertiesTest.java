package group.rober.sql.serialno;

import group.rober.sql.BaseTest;
import group.rober.sql.autoconfigure.SqlProperties;
import group.rober.sql.dialect.SqlDialectType;
import group.rober.sql.serialno.constants.CursorRecordType;
import group.rober.sql.serialno.constants.GeneratorType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class SqlPropertiesTest extends BaseTest {
    @Autowired
    protected SqlProperties properties;

    @Test
    public void test01() {
        Assert.assertNotNull(properties);
        Assert.assertEquals(properties.getSqlDialectType(), SqlDialectType.MYSQL);
        Assert.assertEquals(properties.getSerialNo().getCursorRecord(), CursorRecordType.DB_TABLE);
        //        Assert.assertEquals(properties.getSerialNo().getCursorRecord(), "Redis");
        Assert.assertEquals(properties.getSerialNo().getGeneratorMap().get("demo.Person.personId"),
            GeneratorType.DEFAULT);
        Assert.assertEquals(properties.getSerialNo().getTemplateMap().get("demo.Person.personId"),
            "AS0000");

    }
}

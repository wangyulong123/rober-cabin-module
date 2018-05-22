package group.rober.sql.core;

import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.sql.BaseTest;
import group.rober.sql.entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class DataQueryTest extends BaseTest {

    @Autowired
    protected DataQuery sqlQuery;

    @Test
    public void selectOneTest(){
        Person person = sqlQuery.selectOne(Person.class,"select * from DEMO_PERSON where CODE=:code","code","P1001");
        Assert.assertNotNull(person);
        System.out.println(JSONKit.toJsonString(person));
        Assert.assertEquals("艾伦",person.getName());
    }

    @Test
    public void selectByIdTest(){
        Person person = sqlQuery.selectOneById(Person.class,1);
        Assert.assertNotNull(person);
        Assert.assertEquals("艾伦",person.getName());

        person = sqlQuery.selectOneById(Person.class,MapKit.mapOf("id",1));
        Assert.assertNotNull(person);
        Assert.assertEquals("艾伦",person.getName());

        Assert.assertTrue(sqlQuery.selectExistsById(Person.class,1));
        Assert.assertTrue(sqlQuery.selectExistsById(Person.class,MapKit.mapOf("id",1)));
    }

    @Test
    public void testPaginationQuery(){
        Assert.assertNotNull(sqlQuery);
        Map<String,?> paramMap = MapKit.mapOf("code","P1002");
        PaginationData result = sqlQuery.selectListPagination(Person.class,"select * from DEMO_PERSON where CODE > :code",paramMap,3,15);
        Assert.assertTrue(result.getRowCount()>0);
        System.out.println(JSONKit.toJsonString(result));
    }

    @Test
    public void testSelectCount(){
        Map<String,?> paramMap = MapKit.mapOf("code","P1002");
        int count = sqlQuery.selectCount("select * from DEMO_PERSON where CODE=:code",paramMap);
        Assert.assertEquals(1,count);
    }
}

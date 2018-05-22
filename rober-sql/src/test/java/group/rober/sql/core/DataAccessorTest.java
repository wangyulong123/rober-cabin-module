package group.rober.sql.core;

import group.rober.runtime.kit.DateKit;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.sql.BaseTest;
import group.rober.sql.entity.Person;
import group.rober.sql.entity.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class DataAccessorTest extends BaseTest {
    @Autowired
    protected DataAccessor dataAccessor;

    @Test
    public void selectOneTest(){
//        Person person = dataAccessor.selectOne(Person.class,"select * from DEMO_PERSON where CODE=:code","code","P1001");
        Person person = dataAccessor.selectOne(Person.class,"select * from DEMO_PERSON where CODE LIKE :code ","code","%P1001");
        Assert.assertNotNull(person);
        Assert.assertEquals("艾伦",person.getName());
    }

    @Test
    public void selectByIdTest(){
        Person person = dataAccessor.selectOneById(Person.class,1);
        Assert.assertNotNull(person);
        Assert.assertEquals("艾伦",person.getName());

        person = dataAccessor.selectOneById(Person.class, MapKit.mapOf("id",1));
        Assert.assertNotNull(person);
        Assert.assertEquals("艾伦",person.getName());

        Assert.assertTrue(dataAccessor.selectExistsById(Person.class,1));
        Assert.assertTrue(dataAccessor.selectExistsById(Person.class,MapKit.mapOf("id",1)));
    }

    @Test
    public void testPaginationQuery(){
        Assert.assertNotNull(dataAccessor);
        Map<String,?> paramMap = MapKit.mapOf("code","P1002");
        PaginationData result = dataAccessor.selectListPagination(Person.class,"select * from DEMO_PERSON where CODE > :code",paramMap,3,15);
        Assert.assertTrue(result.getRowCount()>0);
        System.out.println(JSONKit.toJsonString(result));
    }

    @Test
    public void testSelectCount(){
        Map<String,?> paramMap = MapKit.mapOf("code","P1002");
        int count = dataAccessor.selectCount("select * from DEMO_PERSON where CODE=:code",paramMap);
        Assert.assertEquals(1,count);
    }

    //    @Test
    public void insertTest(){
//        updater.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        Person person = new Person();
        person.setId(System.currentTimeMillis()/1000);
        person.setCode("jt");
        person.setName("测试1");
        person.setChnName("测试1");
        person.setEngName("test1");
        person.setHeight(182.3);
        person.setViewTimes(3L);
        person.setBirth(DateKit.parse("1999-8-8"));

        dataAccessor.insert(person);

        boolean exists = dataAccessor.selectExistsById(Person.class,person.getId());
        Assert.assertTrue(exists);

        person = dataAccessor.selectOneById(Person.class,person.getId());
        Assert.assertEquals("测试1",person.getName());

        dataAccessor.delete(person);
//        updater.execute("delete * from DEMO_PERSON where ID=:id",MapKit.mapOf("id",person.getId());

        exists = dataAccessor.selectExistsById(Person.class,MapKit.mapOf("id",person.getId()));
        Assert.assertFalse(exists);
    }

    @Test
    public void testBatch(){

        dataAccessor.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        dataAccessor.insert(TestData.personList);

        List<Person> personList = dataAccessor.selectList(Person.class,"select * from DEMO_PERSON");
        System.out.println(JSONKit.toJsonString(personList));

        dataAccessor.delete(TestData.personList);

    }

    @Test
    public void testSave(){

        dataAccessor.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        dataAccessor.save(TestData.personList);

        List<Person> personList = dataAccessor.selectList(Person.class,"select * from DEMO_PERSON");
        System.out.println(JSONKit.toJsonString(personList));

        dataAccessor.delete(TestData.personList);

    }
}

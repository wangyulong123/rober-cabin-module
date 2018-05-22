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

public class DataUpdaterTest extends BaseTest {
    @Autowired
    protected DataQuery query;
    @Autowired
    protected DataUpdater updater;

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

        updater.insert(person);

        boolean exists = query.selectExistsById(Person.class,person.getId());
        Assert.assertTrue(exists);

        person = query.selectOneById(Person.class,person.getId());
        Assert.assertEquals("测试1",person.getName());

        updater.delete(person);
//        updater.execute("delete * from DEMO_PERSON where ID=:id",MapKit.mapOf("id",person.getId());

        exists = query.selectExistsById(Person.class,MapKit.mapOf("id",person.getId()));
        Assert.assertFalse(exists);
    }

    @Test
    public void testBatch(){

        updater.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        updater.insert(TestData.personList);

        List<Person> personList = query.selectList(Person.class,"select * from DEMO_PERSON");
        System.out.println(JSONKit.toJsonString(personList));

        updater.delete(TestData.personList);

    }

    @Test
    public void testSave(){

        updater.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        updater.save(TestData.personList);

        List<Person> personList = query.selectList(Person.class,"select * from DEMO_PERSON");
        System.out.println(JSONKit.toJsonString(personList));

        updater.delete(TestData.personList);

    }

}

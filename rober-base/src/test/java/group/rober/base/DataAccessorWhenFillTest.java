package group.rober.base;

import group.rober.base.entity.Person;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.sql.core.DataAccessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataAccessorWhenFillTest extends BaseTest {
    @Autowired
    protected DataAccessor dataAccessor;

    @Test
    public void testSaveBatch(){

        dataAccessor.execute("delete from DEMO_PERSON where ID in (701,702,703)",MapKit.newEmptyMap());
        dataAccessor.save(TestData.personList.get(0));
//        dataBeanAccessor.save(TestData.personList);

        List<Person> personList = dataAccessor.selectList(Person.class,"select * from DEMO_PERSON",MapKit.newEmptyMap());
        System.out.println(JSONKit.toJsonString(personList));
//        dataAccessor.update("delete from DEMO_PERSON where ID in (701,702,703)",MapKit.newEmptyMap());

    }
}

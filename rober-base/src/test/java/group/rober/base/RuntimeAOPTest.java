package group.rober.base;

import group.rober.base.entity.Person;
import group.rober.runtime.kit.DateKit;
import group.rober.sql.core.DataAccessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RuntimeAOPTest extends BaseTest {
    @Autowired
    protected DataAccessor dataAccessor;

    @Test
    public void testSave(){
        Person person = new Person();
        person.setId("701");
        person.setCode("jt");
        person.setName("测试1");
        person.setChnName("测试1");
        person.setEngName("test1");
//        person.setRevision(1);
        person.setHeight(182.3);
        person.setViewTimes(3L);
        person.setBirth(DateKit.parse("1999-8-8"));

        dataAccessor.save(person);
    }
}

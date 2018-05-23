package group.rober.dataform.handler.impl;

import group.rober.dataform.BaseTest;
import group.rober.dataform.TestData;
import group.rober.dataform.model.DataForm;
import group.rober.dataform.entity.Person;
import group.rober.dataform.mapper.DataFormMapperTest;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.sql.core.PaginationData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanDataListHandlerTest extends BaseTest {

    @Autowired
    protected BeanDataListHandler<Object> handler;

    @Test
    public void queryTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.getQuery().setFrom("DEMO_PERSON ").setWhere("CODE > :code");
        dataForm.setDataModel(Person.class.getName());

        Map<String,?> param = MapKit.mapOf("code","P102");
        PaginationData<Object> ret = handler.query(dataForm,param,null,null,15,0);
        System.out.println(JSONKit.toJsonString(ret));
    }

    @Test
    public void saveTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.getQuery().setFrom("DEMO_PERSON ").setWhere("CODE = :code");
        dataForm.setDataModel(Person.class.getName());

        List<Object> persons = new ArrayList<Object>();
        persons.addAll(TestData.personList);
//        TestData.personList;
        handler.delete(dataForm, persons);
        handler.insert(dataForm,persons);
        handler.update(dataForm,persons);
        handler.save(dataForm,persons);
    }
}

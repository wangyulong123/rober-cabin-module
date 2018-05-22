package group.rober.dataform.handler.impl;

import group.rober.dataform.BaseTest;
import group.rober.dataform.TestData;
import group.rober.dataform.entity.Person;
import group.rober.dataform.mapper.DataFormMapperTest;
import group.rober.dataform.model.DataForm;
import group.rober.runtime.kit.BeanKit;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class BeanDataOneHandlerTest extends BaseTest {

    @Autowired
    protected BeanDataOneHandler<Object> handler;

    @Test
    public void queryTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.getQuery().setFrom("DEMO_PERSON ").setWhere("CODE = :code");
        dataForm.setDataModel(Person.class.getName());

        Map<String,?> param = MapKit.mapOf("code","P701");
        Object ret = handler.query(dataForm,param);
        System.out.println(JSONKit.toJsonString(ret));



    }

    @Test
    public void saveTest() throws ClassNotFoundException {
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.setDataModel(Person.class.getName());

        Object object = BeanKit.map2Bean(TestData.dataList.get(0),Class.forName(Person.class.getName()));
        handler.delete(dataForm,object);
        handler.insert(dataForm,object);
        handler.update(dataForm,object);
        handler.save(dataForm,object);
    }
}

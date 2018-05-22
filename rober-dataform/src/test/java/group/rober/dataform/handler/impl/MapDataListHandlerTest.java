package group.rober.dataform.handler.impl;

import group.rober.dataform.BaseTest;
import group.rober.dataform.TestData;
import group.rober.dataform.mapper.DataFormMapperTest;
import group.rober.dataform.model.DataForm;
import group.rober.dataform.model.DataFormElement;
import group.rober.dataform.model.types.ElementDataType;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.runtime.lang.MapData;
import group.rober.sql.core.PaginationData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class MapDataListHandlerTest extends BaseTest {
    @Autowired
    protected MapDataListHandler handler;

    @Test
    public void queryTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.getQuery().setFrom("DEMO_PERSON ").setWhere("CODE = :code");
        //把code作一个替换处理
        DataFormElement element = dataForm.getElement("code");
        element.setCode("code1");
        dataForm.addElement(element);
        dataForm.removeElement("code");

        Map<String,?> paramMap = MapKit.mapOf("code","P1002");
        PaginationData<MapData> ret = handler.query(dataForm,paramMap,null,null,15,0);

        Assert.assertTrue(ret.getDataList().size()>1);

        System.out.println(JSONKit.toJsonString(ret));

    }

    @Test
    public void saveTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.getQuery().setFrom("DEMO_PERSON ").setWhere("CODE = :code");
        dataForm.addElement(new DataFormElement("id","ID","唯一编号",null, ElementDataType.Integer).setPrimaryKey(true));
        dataForm.getElement("code").setPrimaryKey(false);


        //把code作一个替换处理
//        DataFormElement element = dataForm.getElement("code");
//        element.setCode("code1");
//        dataForm.addElement(element);
//        dataForm.removeElement("code");

        handler.delete(dataForm, TestData.dataList);
        handler.insert(dataForm,TestData.dataList);
        handler.update(dataForm,TestData.dataList);
        handler.save(dataForm,TestData.dataList);

    }
}

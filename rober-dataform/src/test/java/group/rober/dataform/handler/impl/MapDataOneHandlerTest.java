package group.rober.dataform.handler.impl;

import group.rober.dataform.BaseTest;
import group.rober.dataform.TestData;
import group.rober.dataform.mapper.DataFormMapperTest;
import group.rober.dataform.model.DataForm;
import group.rober.dataform.model.DataFormElement;
import group.rober.dataform.model.types.ElementDataType;
import group.rober.runtime.kit.MapKit;
import group.rober.runtime.lang.MapData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MapDataOneHandlerTest extends BaseTest {

    @Autowired
    protected MapDataOneHandler handler;

    @Test
    public void saveTest(){
        DataForm dataForm = DataFormMapperTest.demoPersonInfo();
        dataForm.addElement(new DataFormElement("id","ID","唯一编号",null, ElementDataType.Integer));
        MapData dataObject = TestData.dataList.get(0);
        handler.delete(dataForm,dataObject);
        handler.save(dataForm,dataObject);
        MapData row = handler.query(dataForm, MapKit.mapOf("code","jt"));
        Assert.assertNotNull(row);
        handler.delete(dataForm,dataObject);
        handler.insert(dataForm,dataObject);
        handler.update(dataForm,dataObject);
//        handler.delete(dataForm,dataObject);
    }
}

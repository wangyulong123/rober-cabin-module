package group.rober.sql.core;

import group.rober.runtime.kit.DateKit;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.runtime.lang.MapData;
import group.rober.sql.BaseTest;
import group.rober.sql.entity.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MapDataAccessorTest extends BaseTest {
    @Autowired
    protected MapDataAccessor dataAccessor;

        @Test
    public void selectOneTest(){
        MapData person = dataAccessor.selectOne("select * from DEMO_PERSON where CODE=:code","code","P1001");
        Assert.assertNotNull(person);
        System.out.println(JSONKit.toJsonString(person));
        Assert.assertEquals("艾伦",person.getValue("name").strValue());
    }

        @Test
    public void selectListTest(){
        List<MapData> personList = dataAccessor.selectList("select * from DEMO_PERSON where CODE>:code","code","P1001");
        Assert.assertNotNull(personList);
        System.out.println(JSONKit.toJsonString(personList));
    }

    @Test
    public void selectPaginationListTest(){
        Map<String,?> paramMap = MapKit.mapOf("code","P1001");
        PaginationData result = dataAccessor.selectListPagination("select * from DEMO_PERSON where CODE>:code",paramMap,2,15);
        System.out.println(JSONKit.toJsonString(result));
    }

    @Test
    public void testInsert(){
        dataAccessor.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        MapData dataObject = TestData.dataList.get(0);
        dataAccessor.insert("DEMO_PERSON",dataObject);
        MapData row = dataAccessor.selectOne("SELECT * FROM DEMO_PERSON where ID=:id",MapKit.mapOf("id",dataObject.getValue("id").intValue()));

        System.out.println(row.toJsonString());

        dataAccessor.execute("delete from DEMO_PERSON where ID=:id",MapKit.mapOf("id",dataObject.getValue("id").intValue()));

    }

    @Test
    public void testSave(){
        dataAccessor.save("DEMO_PERSON",TestData.dataList,TestData.keyList);
    }

    @Test
    public void testSaveDate(){
        MapData object = new MapData();
        object.put("id","PX1001");
        object.put("code","PX1001");
        object.put("name","日期测试");
        object.put("birth", DateKit.now().getTime());

        MapData key = new MapData();
        key.put("id","PX1001");

        dataAccessor.save("DEMO_PERSON",object,key);
    }
}

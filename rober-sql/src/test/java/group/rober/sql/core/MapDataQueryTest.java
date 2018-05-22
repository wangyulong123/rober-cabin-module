package group.rober.sql.core;

import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.MapKit;
import group.rober.runtime.lang.MapData;
import group.rober.sql.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MapDataQueryTest extends BaseTest {
    @Autowired
    protected MapDataQuery dataQuery;

//    @Test
    public void selectOneTest(){
        MapData person = dataQuery.selectOne("select * from DEMO_PERSON where CODE=:code","code","P1001");
        Assert.assertNotNull(person);
        System.out.println(JSONKit.toJsonString(person));
        Assert.assertEquals("艾伦",person.getValue("name").strValue());
    }

//    @Test
    public void selectListTest(){
        List<MapData> personList = dataQuery.selectList("select * from DEMO_PERSON where CODE>:code","code","P1001");
        Assert.assertNotNull(personList);
        System.out.println(JSONKit.toJsonString(personList));
    }

    @Test
    public void selectPaginationListTest(){
        Map<String,?> paramMap = MapKit.mapOf("code","P1001");
        PaginationData result = dataQuery.selectListPagination("select * from DEMO_PERSON where CODE>:code",paramMap,2,15);
        System.out.println(JSONKit.toJsonString(result));
    }
}

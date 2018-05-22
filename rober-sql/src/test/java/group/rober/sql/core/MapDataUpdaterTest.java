package group.rober.sql.core;

import group.rober.runtime.kit.MapKit;
import group.rober.runtime.lang.MapData;
import group.rober.sql.BaseTest;
import group.rober.sql.entity.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MapDataUpdaterTest extends BaseTest {
    @Autowired
    protected MapDataUpdater dataUpdater;
    @Autowired
    protected MapDataQuery dataQuery;

    @Test
    public void testInsert(){
        dataUpdater.execute("delete from DEMO_PERSON where ID in (701,702,703)");
        MapData dataObject = TestData.dataList.get(0);
        dataUpdater.insert("DEMO_PERSON",dataObject);
        MapData row = dataQuery.selectOne("SELECT * FROM DEMO_PERSON where ID=:id",MapKit.mapOf("id",dataObject.getValue("id").intValue()));

        System.out.println(row.toJsonString());

        dataUpdater.delete("DEMO_PERSON",MapData.valueOf("id",dataObject.getValue("id").intValue()));
        dataUpdater.execute("delete from DEMO_PERSON where ID=:id",MapKit.mapOf("id",dataObject.getValue("id").intValue()));
    }

    @Test
    public void testSave(){
        dataUpdater.save("DEMO_PERSON",TestData.dataList,TestData.keyList);
    }

}

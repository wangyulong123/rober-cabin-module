package group.rober.dataform.service;

import group.rober.dataform.BaseTest;
import group.rober.dataform.model.DataFormElement;
import group.rober.runtime.kit.JSONKit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataFormAdminServiceTest extends BaseTest {

    @Autowired
    protected DataFormAdminService adminService;

    @Test
    public void test1(){
//        List<DataFormElement> elements = adminService.parseElementsFromTables("AUTH_USER");
        List<DataFormElement> elements = adminService.parseElementsFromTables("xxi-info","AUTH_USER AS A","AUTH_ORG B","ACT_ID_INFO");
        System.out.println(JSONKit.toJsonString(elements));
    }
}

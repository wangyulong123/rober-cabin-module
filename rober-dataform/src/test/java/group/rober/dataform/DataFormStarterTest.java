package group.rober.dataform;

import group.rober.dataform.mapper.DataFormMapper;
import group.rober.sql.core.DataAccessor;
import org.junit.Assert;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class DataFormStarterTest extends BaseTest {
    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    protected DataAccessor dataAccessor;

    @Test
    public void test(){
        Assert.assertNotNull(dataAccessor);
    }

    @Test
    public void baseAutoConfigTest(){
        Assert.assertNotNull(sqlSessionTemplate);
    }

    @Autowired
    DataFormMapper dataFormMapper;

    @Test
    public void testExist() {
        boolean res = dataFormMapper.exists("workflow-WorkflowParamsList");
        Assert.assertTrue(res);
        res = dataFormMapper.exists("non-exist");
        Assert.assertFalse(res);
    }
}

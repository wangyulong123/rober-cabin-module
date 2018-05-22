package group.rober.base.param.service;

import group.rober.base.BaseTest;
import group.rober.base.param.model.ParamEntry;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhulifeng on 17-12-21.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParamServiceTest extends BaseTest {

    @Autowired
    ParamService paramService;

    @Test
    public void test01GetParam() {
        ParamEntry param = paramService.getParam("systemManager");
        Assert.assertNotNull(param);
    }



}

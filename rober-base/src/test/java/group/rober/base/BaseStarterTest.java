package group.rober.base;

import group.rober.sql.core.DataAccessor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseStarterTest extends BaseTest{
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DataAccessor dataAccessor;
    @Autowired
    protected CacheManager cacheManager;

    @Test
    public void runtimeTest(){
        Assert.assertNotNull(jdbcTemplate);
        Assert.assertNotNull(dataAccessor);
    }

    @Test
    public void testBase(){
        Assert.assertNotNull(cacheManager);
    }
}

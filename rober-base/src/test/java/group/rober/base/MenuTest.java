package group.rober.base;

import group.rober.base.menu.model.MenuEntry;
import group.rober.sql.core.DataAccessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by luyu on 2017/12/19.
 */
public class MenuTest extends BaseTest {

    @Autowired
    protected DataAccessor dataAccessor;

    @Test
    public void testGetMenu() {
        List<MenuEntry> menuEntries = MenuTestData.menuEntries;
        dataAccessor.save(menuEntries);
    }
}

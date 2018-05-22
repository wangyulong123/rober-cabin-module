package group.rober.runtime;

import group.rober.runtime.holder.MessageHolder;
import org.junit.Test;

public class MessageSourceTest extends BaseTest {

    @Test
    public void test1(){
        System.out.println(MessageHolder.getMessage("默认","rober.runtime.test.message"));
    }
}

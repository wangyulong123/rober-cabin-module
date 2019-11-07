package group.rober.runtime.kit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringKitTest {
    @Test
    public void test01(){
        System.out.println(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase("PersonName"),"_"));
        System.out.println(StringUtils.join(StringUtils.splitByCharacterType("PersonName"),"_"));
//        System.out.println(StringUtils.join(StringUtils.s,"_"));
    }

    @Test
    public void test2(){
        System.out.println(StringKit.camelToUnderline("PersonName"));
        System.out.println(StringKit.camelToUnderline("nameI18nCode"));
        System.out.println(StringKit.camelToUnderline("subGroupI18nCode"));
        System.out.println(StringKit.camelToUnderline("PERSON_NAME"));
        System.out.println(StringKit.underlineToCamel("PERSON_NAME"));
    }
}

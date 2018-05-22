package group.rober.base.controller;

import group.rober.base.BaseTest;
import group.rober.runtime.kit.JSONKit;
import group.rober.runtime.kit.NumberKit;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


public class DictControllerTest extends BaseTest {

    @Test
    public void getIndustryCodeTree() throws Exception {
        //构建请求对象
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/base/dicts/Industry/tree")
                ;

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }
}

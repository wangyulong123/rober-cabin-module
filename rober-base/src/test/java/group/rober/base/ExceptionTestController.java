package group.rober.base;

import group.rober.runtime.lang.BizException;
import group.rober.runtime.lang.RoberException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExceptionTestController {
    @RequestMapping(path = "/RuntimeEx")
    public void runtimeEx() {
        throw new RuntimeException();
    }

    @RequestMapping(path = "/RoberException")
    public void roberEx() {
        throw new RoberException();
    }

    @RequestMapping(path = "/BizException")
    public void bizEx() {
        throw new BizException();
    }

    @RequestMapping(path = "/IOException")
    public void ioEx() throws IOException{
        throw new IOException();
    }
}

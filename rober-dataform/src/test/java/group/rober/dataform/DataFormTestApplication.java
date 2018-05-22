package group.rober.dataform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataFormTestApplication {
    public static void main(String[] args){
        SpringApplication.run(DataFormTestApplication.class,args);
    }

}


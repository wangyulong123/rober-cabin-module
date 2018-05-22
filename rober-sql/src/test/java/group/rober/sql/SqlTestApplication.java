package group.rober.sql;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("group.rober.**.mapper")
public class SqlTestApplication {
    public static void main(String[] args){
        SpringApplication.run(SqlTestApplication.class,args);
    }
}

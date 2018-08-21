package top.icinghuan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.icinghuan.demo.dao.support.DaoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@Import({DaoConfiguration.class})
public class JavaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaDemoApplication.class, args);
    }
}

package top.icinghuan.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.icinghuan.demo.dao.MyAopDao;
import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author : xy
 * @date : 2018/9/5
 * Description :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTest {
    @Autowired
    private MyAopDao myAopDao;

    @Test
    public void aopTest() {
        User user = new User();
        myAopDao.getName(user);
    }
}

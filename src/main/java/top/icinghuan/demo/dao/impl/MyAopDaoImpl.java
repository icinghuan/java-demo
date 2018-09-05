package top.icinghuan.demo.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import top.icinghuan.demo.dao.MyAopDao;
import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author : xy
 * @date : 2018/9/5
 * Description :
 */
@Slf4j
@Repository
public class MyAopDaoImpl implements MyAopDao {
    @Override
    public void getUser() {
        System.out.println("AOP getUser()");
    }

    @Override
    public void getName(User user) {
        System.out.println("AOP getName()");
    }

    @Override
    public void addUser() {
        System.out.println("AOP addUser()");
    }
}

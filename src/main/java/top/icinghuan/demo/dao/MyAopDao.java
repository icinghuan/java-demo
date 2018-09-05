package top.icinghuan.demo.dao;

import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author : xy
 * @date : 2018/9/5
 * Description :
 */
public interface MyAopDao {
    void getUser();

    void getName(User user);

    void addUser();
}

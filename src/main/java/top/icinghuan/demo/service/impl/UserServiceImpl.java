package top.icinghuan.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.icinghuan.demo.dao.UserDao;
import top.icinghuan.demo.model.tables.generated.User;
import top.icinghuan.demo.service.UserService;

/**
 * @author : xy
 * @date : 2018/8/21
 * Description :
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Boolean addUser(User user) {
        return userDao.insertSelective(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return userDao.selectByUserId(userId);
    }

    @Override
    public User getByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }

    @Override
    public Boolean removeByUserId(Long userId) {
        return userDao.deleteByUserId(userId);
    }
}

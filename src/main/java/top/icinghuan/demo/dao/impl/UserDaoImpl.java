package top.icinghuan.demo.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.icinghuan.demo.dao.DaoBase;
import top.icinghuan.demo.dao.UserDao;
import top.icinghuan.demo.model.tables.generated.User;
import top.icinghuan.demo.model.tables.generated.UserExample;
import top.icinghuan.demo.model.tables.generated.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/8/21
 * Description :
 */
@Slf4j
@Repository
public class UserDaoImpl extends DaoBase implements UserDao {

    private UserMapper getMapper() {
        return mapper(UserMapper.class);
    }

    @Override
    public Boolean insertSelective(User user) {
        return getMapper().insertSelective(user) > 0;
    }

    @Override
    public User selectByUserId(Long userId) {
        return getMapper().selectByPrimaryKey(userId);
    }

    @Override
    public User selectByUserName(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<User> list = getMapper().selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<User> selectListByUserName(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUserNameLike("%" + userName + "%");
        List<User> list = getMapper().selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        return getMapper().deleteByPrimaryKey(userId) > 0;
    }
}

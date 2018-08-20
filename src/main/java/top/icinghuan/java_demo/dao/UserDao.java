package top.icinghuan.java_demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.icinghuan.java_demo.model.tables.generated.User;
import top.icinghuan.java_demo.model.tables.generated.UserExample;
import top.icinghuan.java_demo.model.tables.generated.UserMapper;

import java.util.List;

/**
 * @author icinghuan
 * @date 2018/8/20 23:45
 * Description:
 */
@Slf4j
@Repository
public class UserDao extends BaseDao {
    private UserMapper getMapper() {
        return mapper(UserMapper.class);
    }

    public Boolean insertSelective(User user) {
        return getMapper().insertSelective(user) > 0;
    }

    public User selectByUserId(Long userId) {
        return getMapper().selectByPrimaryKey(userId);
    }

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

    public Boolean deleteByUserId(Long userId) {
        return getMapper().deleteByPrimaryKey(userId) > 0;
    }
}

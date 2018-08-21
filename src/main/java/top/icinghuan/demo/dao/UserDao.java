package top.icinghuan.demo.dao;

import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author icinghuan
 * @date 2018/8/20 23:45
 * Description:
 */
public interface UserDao{

    public Boolean insertSelective(User user);

    public User selectByUserId(Long userId);

    public User selectByUserName(String userName);

    public Boolean deleteByUserId(Long userId);
}

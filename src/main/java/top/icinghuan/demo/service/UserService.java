package top.icinghuan.demo.service;

import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author : xy
 * @date : 2018/8/21
 * Description :
 */
public interface UserService {

    public Boolean addUser(User user);

    public User getByUserId(Long userId);

    public User getByUserName(String userName);

    public Boolean removeByUserId(Long userId);
}

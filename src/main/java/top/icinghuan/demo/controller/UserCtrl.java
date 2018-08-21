package top.icinghuan.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.icinghuan.demo.controller.req.UserReq;
import top.icinghuan.demo.model.tables.generated.User;
import top.icinghuan.demo.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author : xy
 * @date : 2018/8/21
 * Description :
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserCtrl extends BaseController {
    @Resource
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid UserReq userReq) {
        User optional = userService.getByUserName(userReq.getUserName());
        if (optional != null) {
            return BadRequest(jsonMsg("bad_request", "用户名已存在"));
        }
        User user = new User();
        BeanUtils.copyProperties(userReq, user);
        userService.addUser(user);
        return Ok(jsonOk(user.getUserId()));
    }

}

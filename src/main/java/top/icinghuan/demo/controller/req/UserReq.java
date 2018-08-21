package top.icinghuan.demo.controller.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : xy
 * @date : 2018/8/21
 * Description :
 */
@Data
public class UserReq {
    @NotNull
    String userName;
    @NotNull
    String userPassword;
}

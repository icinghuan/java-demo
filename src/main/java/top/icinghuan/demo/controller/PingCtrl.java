package top.icinghuan.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xy
 * @date : 2018/9/6
 * Description :
 */
@Slf4j
@RestController
@RequestMapping("/ping")
public class PingCtrl {

    @RequestMapping
    @ResponseBody
    public String ping() {
        return "pong";
    }
}

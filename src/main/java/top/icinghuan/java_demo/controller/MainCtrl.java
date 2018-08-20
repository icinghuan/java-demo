package top.icinghuan.java_demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xy
 * @date : 2018/8/17
 * Description :
 */
@Slf4j
@RestController
@RequestMapping("/")
public class MainCtrl extends BaseController {

    @GetMapping("/hello")
    public ResponseEntity hello(@RequestParam(required = false) String name) {
        String result;
        if (StringUtils.isBlank(name)) {
            result = "Hello world!";
        } else {
            result = "Hello " + name + "!";
        }
        return Ok(jsonOk(result));
    }
}

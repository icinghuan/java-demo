package top.icinghuan.demo.javatest;

import lombok.Data;
import top.icinghuan.demo.common.JsonResult;

/**
 * @author : xy
 * @date : 2018/8/17
 * Description :
 */
public class JsonTest {
    @Data
    class Asset {
        String type;
        Long amount;
    }

    public static void main(String[] args) {
        JsonTest jsonTest = new JsonTest();
        Asset asset = jsonTest.new Asset();
        asset.setType("cash");
        asset.setAmount(10000L);
        System.out.println(JsonResult.ok(asset));;
        System.out.println(asset);
    }
}

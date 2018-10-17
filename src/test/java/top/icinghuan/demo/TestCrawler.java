package top.icinghuan.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import top.icinghuan.demo.common.util.HttpUtil;

/**
 * @author : xy
 * @date : 2018/10/17
 * Description :
 */
public class TestCrawler {
    private static String URL = "";

    @Test
    public void testCrawler() {
        String msgStr = HttpUtil.get(URL);
        JSONObject jsonObject = JSON.parseObject(msgStr);
        Object object = JSONObject.toJavaObject(jsonObject, Object.class);

    }
}

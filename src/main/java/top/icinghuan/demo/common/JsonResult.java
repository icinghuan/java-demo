package top.icinghuan.demo.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JsonResult<T> {

    public static final String OK = "ok";

    public static final String FAIL = "fail";

    private String status;

    private String msg;

    private T data;

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setStatus(OK);
        result.setMsg(OK);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static JsonResult fail(String status, String msg) {
        if (status.equals(OK)) {
            throw new RuntimeException("ok is not fail");
        }
        JsonResult result = new JsonResult();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    public static JsonResult fail(String msg){
        JsonResult result = new JsonResult();
        result.setStatus(FAIL);
        result.setMsg(msg);
        return result;
    }

    public static JsonResult badRequest(String msg) {
        return fail("bad_request", msg);
    }

    @JsonIgnore
    public boolean isOk() {
        return OK.equals(status);
    }

    @JsonGetter
    public long getTimestamp() {
        return System.currentTimeMillis();
    }
}


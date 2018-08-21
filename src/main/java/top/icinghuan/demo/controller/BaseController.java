package top.icinghuan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import top.icinghuan.demo.common.JsonResult;

import java.text.MessageFormat;

public abstract class BaseController {

    @Autowired
    private MessageSource messageSource;

    protected ResponseEntity Ok(Object data) {
        return responseEntity(HttpStatus.OK, data);
    }

    protected ResponseEntity BadRequest(Object data) {
        return responseEntity(HttpStatus.BAD_REQUEST, data);
    }

    protected ResponseEntity NotFound(Object data) {
        return responseEntity(HttpStatus.NOT_FOUND, data);
    }

    protected ResponseEntity InternalError(Object data) {
        return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    protected ResponseEntity RepeatInsertError(Object data) {
        return responseEntity(HttpStatus.CREATED, data);
    }

    protected ResponseEntity Unauthorized(Object data) {
        return responseEntity(HttpStatus.UNAUTHORIZED, data);
    }

    protected ResponseEntity Forbidden(Object data) {
        return responseEntity(HttpStatus.FORBIDDEN, data);
    }

    private ResponseEntity responseEntity(HttpStatus httpStatus, Object data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (data instanceof JsonResult) {
            responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            JsonResult res = (JsonResult) data;
            res.setMsg(getMessage(res.getMsg()));
        }
        return new ResponseEntity(data, responseHeaders, httpStatus);
    }

    private String getMessage(String code, Object... args) {
        try {
            return MessageFormat.format(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()), args);
        } catch (Exception e) {
            return code;
        }
    }

    protected JsonResult jsonOk(Object data) {
        return JsonResult.ok(data);
    }

    protected JsonResult jsonOk() {
        return jsonOk(null);
    }

    protected JsonResult jsonFail(String msg){
        return JsonResult.fail(msg);
    }

    protected JsonResult jsonMsg(String status, String msg) {
        return JsonResult.fail(status, msg);
    }

    protected JsonResult jsonMsg(Errors errors) {
        if (errors.hasErrors()) {
            ObjectError error = errors.getAllErrors().get(0);
            String msg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            if (error instanceof FieldError) {
                return JsonResult.fail("bad_request", String.format("%s (%s)", msg, ((FieldError) error).getField()));
            } else {
                return JsonResult.fail("bad_request", msg);
            }
        } else {
            return JsonResult.fail("bad_request", "Bad Request");
        }
    }

}

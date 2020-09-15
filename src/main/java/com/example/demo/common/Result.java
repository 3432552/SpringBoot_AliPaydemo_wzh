package com.example.demo.common;

import java.util.HashMap;

/**
 * 统一数据返回类
 */
public class Result extends HashMap<String, Object> {
    public Result() {
        put("code", 200);
        put("msg", "操作成功");
    }

    public Result(Integer code, String msg) {
        put("code", code);
        put("msg", msg);
    }

    public static Result ok() {
        return new Result();
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result okDate(Object data) {
        Result result = new Result();
        result.put("data", data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.put("code", 500);
        result.put("msg", "操作失败");
        return result;
    }

    public static Result error(Object msg) {
        Result result = new Result();
        result.put("code", 500);
        result.put("msg", msg);
        return result;
    }

    //自定义枚举成功信息
    public static Result okEnum(Integer code, String msg) {
        Result result = new Result(code, msg);
        return result;
    }

    //自定义枚举失败信息
    public static Result errorEnum(Integer code, String msg) {
        Result result = new Result(code, msg);
        return result;
    }
}

package com.teleport.config;

import lombok.Data;

@Data
public class R {

    private Integer code;

    private Object data;

    private String message;

    public static R success() {
        R r = new R();
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(200);
        r.setData(data);
        r.setMessage("成功");
        return r;
    }

    public static R error(String massage) {
        R r = new R();
        r.setCode(500);
        r.setMessage(massage);
        return r;
    }
}

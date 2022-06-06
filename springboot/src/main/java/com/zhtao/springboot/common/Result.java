package com.zhtao.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.common
 * @ClassName: Result
 * @Date 2022/6/6 12:04
 */

/**
 * 街头统一返回包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;


    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }
}

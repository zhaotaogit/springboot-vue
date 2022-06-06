package com.zhtao.springboot.exception;

import lombok.Getter;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.exception
 * @ClassName: ServiceException
 * @Date 2022/6/6 12:22
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code  = code;
    }
}

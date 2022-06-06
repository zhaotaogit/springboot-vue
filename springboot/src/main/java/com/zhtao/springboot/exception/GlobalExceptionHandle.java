package com.zhtao.springboot.exception;

import com.zhtao.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.exception
 * @ClassName: ExceptionHandle
 * @Date 2022/6/6 12:20
 */
@ControllerAdvice
public class GlobalExceptionHandle {


    /**
     * 全局异常捕捉处理
     *
     * @param se
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public Result errorHandler(ServiceException se) {
        return Result.error(se.getCode(), se.getMessage());
    }
}

package com.xdu.yygh.common.exception;

import com.xdu.yygh.common.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//使其可以作为json输出
    public Result error(Exception e){
        e.printStackTrace();
        return  Result.fail();
    }

//    @ExceptionHandler(YyghException.class)
//    @ResponseBody//使其可以作为json输出
//    public Result error(YyghException e){
//        e.printStackTrace();
//        return  Result.fail();
//    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        return Result.build(e.getCode(), e.getMessage());
    }



}

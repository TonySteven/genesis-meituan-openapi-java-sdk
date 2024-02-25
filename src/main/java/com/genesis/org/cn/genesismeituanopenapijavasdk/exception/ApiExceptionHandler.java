package com.genesis.org.cn.genesismeituanopenapijavasdk.exception;

import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.StatusCodeEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.exception.DyBusinessException;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ApiExceptionHandler {

    /**
     * 捕获用户验证抛出的异常
     */

    @ExceptionHandler(BindException.class)
    public ApiResult<String> bindException(BindException bindException) {
        List<ObjectError> allErrors = bindException.getAllErrors();

        for (ObjectError allError : allErrors) {
            log.warn("参数校验错误信息，错误信息:{}", allError.getDefaultMessage());
        }
        return ApiResult.error(StatusCodeEnums.BAD_REQUEST.getCode(), allErrors.get(0).getDefaultMessage());
    }

    /**
     * 业务异常处理
     * @param e 业务异常信息
     * @return 结果
     */
    @ExceptionHandler({DyBusinessException.class,BusinessException.class})
    public ApiResult<String> BusinessExceptionHandler(Exception e) {
        log.warn("业务异常:{}",e.getMessage(),e);
        if(e instanceof DyBusinessException st){
            return ApiResult.error(st.getCode().get(), StringUtils.defaultString(st.getMsg().get(),e.getMessage()));
        }else if(e instanceof BusinessException st){
            return ApiResult.error(st.getCode().get(), StringUtils.defaultString(st.getMsg().get(),e.getMessage()));
        }
        return exceptionHandler(e);
    }

    /**
     * 系统异常处理
     * @param e 异常信息
     * @return 结果
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception e) {
        log.error("系统异常:{}",e.getMessage(),e);
        return ApiResult.error(e.getMessage());
    }

}

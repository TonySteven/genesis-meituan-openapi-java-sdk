package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.function.Supplier;

/**
 * 异常处理工具类
 */
@Slf4j
@UtilityClass
public class ExceptionUtils {

    /**
     * 异常重试
     * @param supplier 重试方法
     * @param retryCount 重试计数
     * @param retryAttempts 重试次数
     * @return 重试结果
     * @param <T> 入参对象类型
     */
    public static <T> T retry(Supplier<T> supplier, int retryCount, int retryAttempts){
        try {
            return supplier.get();
        }catch (Exception e){
            if(isTimeout(e)){
                if(retryCount >= retryAttempts){
                    throw e;
                }else{
                    return retry(supplier, retryCount + 1, retryAttempts);
                }
            }
            throw e;
        }
    }

    public static boolean isTimeout(Exception e){
        return e.getCause() instanceof SocketTimeoutException || e.fillInStackTrace() instanceof SocketTimeoutException;
    }
}

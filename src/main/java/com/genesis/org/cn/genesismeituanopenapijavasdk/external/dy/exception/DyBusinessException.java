package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.exception;

import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.StatusCodeEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.enums.DyHttpStatusEnum;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 服务层异常
 *
 * @author liwenchao
 **/
@Getter
public class DyBusinessException extends RuntimeException {

    /**
     * 枚举码
     */
    public final AtomicReference<Integer> code = new AtomicReference<>();
    /**
     * 枚举描述
     */
    public final AtomicReference<String> msg = new AtomicReference<>();

    public DyBusinessException() {
        super();
    }

    public DyBusinessException(DyHttpStatusEnum statusCodeEnum) {
        super(statusCodeEnum.getValue());
        this.msg.set(statusCodeEnum.getValue());
        this.code.set(statusCodeEnum.getCode());
    }

    public DyBusinessException(StatusCodeEnums statusCodeEnum, String msg) {
        super(msg);
        this.msg.set(msg);
        this.code.set(statusCodeEnum.getCode());
    }

    public DyBusinessException(Integer code, String msg) {
        super(msg);
        this.msg.set(msg);
        this.code.set(code);
    }

    public DyBusinessException(String msg, Integer code) {
        super(msg);
        this.msg.set(msg);
        this.code.set(code);
    }

    public DyBusinessException(String msg) {
        super(msg);
        this.msg.set(msg);
        this.code.set(StatusCodeEnums.UNKNOWN_ERR.getCode());
    }

    @Override
    public String toString() {
        return this.msg.get();
    }

}

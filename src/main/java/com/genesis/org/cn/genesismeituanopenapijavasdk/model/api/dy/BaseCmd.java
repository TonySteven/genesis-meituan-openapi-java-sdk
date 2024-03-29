package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseCmd {

    /**
     * 请求ID不能为空
     */
    @NotBlank(message = "请求ID不能为空")
    private String requestId;
}

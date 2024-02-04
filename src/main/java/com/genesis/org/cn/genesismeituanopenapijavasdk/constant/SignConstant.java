package com.genesis.org.cn.genesismeituanopenapijavasdk.constant;

/**
 * 签名常量
 * @author LP
 * @since 2024-01-27
 */
public interface SignConstant {

    /**
     * 支持加密方式
     */
    String CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * AES加密
     */
    String CIPHER_AES = "AES";

    /**
     * 加密字符串拼接符号
     */
    String SPLICING_SYMBOLS = "&";

    /**
     * 密钥填充符号
     */
    String SECRET_FILLER = "#";
}

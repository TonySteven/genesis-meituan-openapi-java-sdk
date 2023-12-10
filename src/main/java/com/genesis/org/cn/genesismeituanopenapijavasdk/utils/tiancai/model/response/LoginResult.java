package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * login result
 *
 * @author steven
 * &#064;date  2023/10/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {

    /**
     * msg
     */
    private String msg;
    /**
     * access token
     */
    private String accessToken;
    /**
     * refresh token
     */
    private String refreshToken;
    /**
     * code
     */
    private String code;
    /**
     * expires in
     */
    private int expiresIn;

}

package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录对象
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {

    /**
     * user name to login
     */
    private String userNameToLogin;

    /**
     * pass word to log in
     */
    private String passWordToLogin;

}

package com.wanmen.model.users;

import com.wanmen.model.BaseCase;
import lombok.Data;

/**
 * 登录接口用例
 *
 * @author sol
 * @create 2020-06-30  12:22 下午
 */
@Data
public class LoginCase extends BaseCase {
    private String account;
    private String password;
}

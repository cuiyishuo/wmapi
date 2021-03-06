package com.wanmen.model.users;

import lombok.Data;

/**
 * 登录接口用例
 *
 * @author sol
 * @create 2020-06-30  12:22 下午
 */
@Data
public class LoginCase{
    private int id;
    private String caseName;
    private String expected;
    private String api;
    private String scenes;
    private String account;
    private String password;
    private int isDel;
}

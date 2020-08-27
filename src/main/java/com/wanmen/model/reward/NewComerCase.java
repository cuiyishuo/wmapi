package com.wanmen.model.reward;

import lombok.Data;

import java.util.List;

/**
 * @author sol
 * @create 2020-08-20  11:00 下午
 */
@Data
public class NewComerCase {
    private int id;
    private String caseName;
    private String expected;
    private String api;
    private String scenes;
    private String account;
    private String password;
    private String interfaces;
    private int isDel;
    private String courseIds;
}

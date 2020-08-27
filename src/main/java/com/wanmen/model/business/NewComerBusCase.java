package com.wanmen.model.business;

import com.wanmen.model.reward.NewComerCase;
import com.wanmen.model.users.LoginCase;
import lombok.Data;

/**
 * @author sol
 * @create 2020-08-24  12:14 下午
 */
@Data
public class NewComerBusCase {
    private int id;
    private String caseName;
    private String expected;
    private String scenes;
    private int isDel;
    private LoginCase loginCase;
    private NewComerCase newComerCase;
}

package com.wanmen.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 响应信息
 *
 * @author sol
 * @create 2020-06-24  4:33 下午
 */
@Data
public class ResponseModel {
    // 响应头
    private Map headers;
    // 响应体
    private List<Map<String,Object>> body;
    // 响应状态码
    private int statusCode;
}

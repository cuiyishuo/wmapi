package com.wanmen.cases;

import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


@Data
public class TestConfig {

    //用来存储cookies信息的变量
    public static CookieStore store;
    //声明http客户端
    public static CloseableHttpClient httpclient;

}

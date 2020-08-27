package com.wanmen.cases;

import com.alibaba.fastjson.JSONObject;
import com.wanmen.mappers.DataBaseInit;
import com.wanmen.util.ConfigFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * @author sol
 * @create 2020-06-30  10:52 上午
 */
@Slf4j
public class Prepare {

    /**
     * 获取登录token
     *
     * @param account
     * @param password
     * @return
     * @throws IOException
     */
    public static String getAuthorization(String account, String password) throws IOException {
        String authorization = "";
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost (ConfigFile.getAddress ("/main/signin"));
        JSONObject param = new JSONObject ();
        param.put ("account", account);
        param.put ("password", password);
        //设置请求头信息 设置header
        post.setHeader ("content-type", "application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity (param.toString (), "utf-8");
        post.setEntity (entity);
        //声明一个对象来进行响应结果的存储
        String result;
        // 执行post方法
        CloseableHttpResponse response = TestConfig.httpclient.execute (post);
        try {
            authorization = response.getFirstHeader ("Authorization").getValue ();
        } catch (NullPointerException e) {
            throw new NullPointerException ("登录失败，获取登录token异常");
        }
        //关闭结果集
        response.getEntity ().getContent ().close ();
        return authorization;


    }
}

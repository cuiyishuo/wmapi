package com.wanmen.cases.users;

import com.alibaba.fastjson.JSONObject;
import com.wanmen.cases.Prepare;
import com.wanmen.config.TestConfig;
import com.wanmen.datas.SharedData;
import com.wanmen.datas.users.CaseDataProvider;
import com.wanmen.model.ResponseModel;
import com.wanmen.model.users.LoginCase;
import com.wanmen.util.ConfigFile;
import com.wanmen.util.JSONPathMi;
import com.wanmen.util.ConvertResponse;
import com.wanmen.util.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginTest {
    String requestUrl;
    SqlSession session;

    @BeforeClass(description = "测试准备工作,获取HttpClient对象")
    public void beforeTest() throws IOException {
        TestConfig.httpclient = HttpClients.createDefault ();
        session = DataBaseUtil.getSqlSession ();
        requestUrl = ConfigFile.getAddress ("/main/signin");
    }

    @Test(groups = "loginTrue", dataProvider = "loginProvider", dataProviderClass = CaseDataProvider.class, description = "输入正确的账号密码")
    public void loginTrue(LoginCase loginCase) throws IOException {
        System.err.println ("执行用例：【"+loginCase.getCaseName ()+"】");
        // 获取httpclient请求后响应数据
        ResponseModel responseModel = getResult (loginCase);
        String expected = loginCase.getExpected ();
        // 验证响应体用户id
        List<Map<String, Object>> responseModelBody = responseModel.getBody ();
        String email = JSONPathMi.eval (responseModelBody, "$[0].id");
        String emailExpect = JSONPathMi.eval (expected, "$.id");
        Assert.assertEquals (email, emailExpect, loginCase.getCaseName ()+"[登录失败，账号不匹配]");
        // 验证响应头token
        Map responseModelHeaders = responseModel.getHeaders ();
        String authorization = responseModelHeaders.get ("Authorization").toString ();
        String authorizationExpect = JSONPathMi.eval (expected, "$.authorization");
        Assert.assertTrue (authorization.contains (authorizationExpect), loginCase.getCaseName ()+"[登录失败，鉴权token未获取到]");
    }

    @Test(groups = "loginFalse", dataProvider = "loginProvider", dataProviderClass = CaseDataProvider.class, description = "输入异常的账号密码")
    public void loginFalse(LoginCase loginCase) throws IOException {
        System.err.println ("执行用例：【"+loginCase.getCaseName ()+"】");
        // 获取httpclient请求后响应数据
        ResponseModel responseModel = getResult (loginCase);
        String expected = loginCase.getExpected ();
        // 验证响应体用户id
        List<Map<String, Object>> responseModelBody = responseModel.getBody ();
        String email = JSONPathMi.eval (responseModelBody, "$[0].message");
        String emailExpect = JSONPathMi.eval (expected, "$.message");
        Assert.assertEquals (email, emailExpect, loginCase.getCaseName ()+"[异常信息提示错误]");
    }

    @AfterClass
    public void afterTest() {
        session.close ();
    }

    private ResponseModel getResult(LoginCase loginCase) throws IOException {
        ResponseModel responseModel = new ResponseModel ();
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost (requestUrl);
        JSONObject param = new JSONObject ();
        param.put ("account", loginCase.getAccount ());
        param.put ("password", loginCase.getPassword ());
        //设置请求头信息 设置header
        post.setHeader ("content-type", "application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity (param.toString (), "utf-8");
        post.setEntity (entity);
        //声明一个对象来进行响应结果的存储
        String result;
        // 执行post方法
        CloseableHttpResponse response = TestConfig.httpclient.execute (post);
        // 获取响应结果
        result = EntityUtils.toString (response.getEntity (), "utf-8");
        // 将响应数据放入响应实体类
        responseModel.setStatusCode (response.getStatusLine ().getStatusCode ());
        responseModel.setHeaders (ConvertResponse.headerArrToMap (response.getAllHeaders ()));
        responseModel.setBody (ConvertResponse.resEntityToList (result));
        return responseModel;
    }
}

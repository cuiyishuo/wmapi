package com.wanmen.cases.reward;

import com.wanmen.cases.Prepare;
import com.wanmen.cases.TestConfig;
import com.wanmen.datas.reward.NewComerDataProvider;
import com.wanmen.model.ResponseModel;
import com.wanmen.model.reward.NewComerCase;
import com.wanmen.util.ConfigFile;
import com.wanmen.util.ConvertResponse;
import com.wanmen.util.JSONPathMi;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 新人礼包测试
 *
 * @author sol
 * @create 2020-08-20  10:48 下午
 */
public class NewComerTest {
    private String authorization;
    private String requestUrl;

    public NewComerTest() {
        TestConfig.httpclient = HttpClients.createDefault ();
        requestUrl = ConfigFile.getAddress ("/reward/newcomer-bonus");
    }

    @Test(groups = "get_success", dataProvider = "newComerProvider", dataProviderClass = NewComerDataProvider.class, description = "新用户成功激活课程")
    public void newComer2(NewComerCase newComerCase) throws IOException {
        System.err.println ("执行用例：【" + newComerCase.getCaseName () + "】");
        // 获取登录token
        authorization = Prepare.getAuthorization (newComerCase.getAccount (), newComerCase.getPassword ());
        System.out.println (authorization);
        // 获取httpclient请求后响应数据
        ResponseModel responseModel = getResult (newComerCase, authorization);
        String expected = newComerCase.getExpected ();
        // 验证响应体用户id
        List<Map<String, Object>> responseModelBody = responseModel.getBody ();
        String email = JSONPathMi.eval (responseModelBody, "$[0].message");
        String emailExpect = JSONPathMi.eval (expected, "$.message");
        Assert.assertEquals (email, emailExpect, newComerCase.getCaseName () + "[异常信息提示错误]");
    }

    public ResponseModel getResult(NewComerCase newComerCase, String authorization) throws IOException {
        ResponseModel responseModel = new ResponseModel ();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create ().
                addTextBody ("courseIds", newComerCase.getCourseIds ());
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost (requestUrl);
        //设置请求头信息 设置header
        post.setHeader ("authorization", authorization);
        HttpEntity multipart = builder.build ();
        post.setEntity (multipart);
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

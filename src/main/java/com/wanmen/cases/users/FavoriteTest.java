package com.wanmen.cases.users;

import com.wanmen.cases.Prepare;
import com.wanmen.cases.TestConfig;
import com.wanmen.datas.SharedData;
import com.wanmen.datas.users.CaseDataProvider;
import com.wanmen.model.content.BaseCase;
import com.wanmen.model.ResponseModel;
import com.wanmen.util.ConfigFile;
import com.wanmen.util.ConvertResponse;
import com.wanmen.mappers.DataBaseInit;
import com.wanmen.util.JSONPathMi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏
 *
 * @author sol
 * @create 2020-07-02  10:54 下午
 */
public class FavoriteTest {
    String requestUrl;
    SqlSession session;

    /*@BeforeClass(description = "测试准备工作,获取HttpClient对象")
    public void beforeClass() throws IOException {
        TestConfig.httpclient = HttpClients.createDefault ();
        session = DataBaseInit.getSqlSession ();
        requestUrl = ConfigFile.getAddress ("/me/courses/fav");
    }*/

    public FavoriteTest() throws IOException{
        TestConfig.httpclient = HttpClients.createDefault ();
        session = DataBaseInit.getSqlSession ();
        requestUrl = ConfigFile.getAddress ("/me/courses/fav");
    }

    @Test(groups = "getFav", dataProvider = "baseProvider", dataProviderClass = CaseDataProvider.class, description = "获取用户收藏" ,timeOut=30000)
    public void share(BaseCase baseCase) throws IOException {
        // 获取httpclient请求后响应数据
        ResponseModel responseModel = getResult (baseCase.getAccount (), baseCase.getPassword ());
        String expected = baseCase.getExpected ();
        // 获取实际数据长度
        List<Map<String, Object>> resbodys = responseModel.getBody ();
        Integer favSize = resbodys.size ();
        // 获取预期长度
        Integer expectedFavSize = Integer.valueOf (JSONPathMi.eval (expected, "$.size"));
        Assert.assertEquals (favSize, expectedFavSize);
    }

    public ResponseModel getResult(String account, String password) throws IOException {
        ResponseModel responseModel = new ResponseModel ();
        // 获取登录token
        SharedData.authorization = Prepare.getAuthorization (account, password);
        // 构造出：https://beta-www.wanmen.org/me/courses/fav?wd=java&wd2=py
        System.out.println (SharedData.authorization);
        /*URI uri = new URIBuilder (requestUrl)
                .setParameter("wd", "java")
                .setParameter ("wd2", "py").build();*/
        //下边的代码为写完接口的测试代码
        HttpGet httpGet = new HttpGet (requestUrl);
        //设置请求头信息 设置header
        httpGet.setHeader ("authorization", SharedData.authorization);
        //声明一个对象来进行响应结果的存储
        String result;
        // 执行get方法
        CloseableHttpResponse response = TestConfig.httpclient.execute (httpGet);
        // 获取响应结果
        result = EntityUtils.toString (response.getEntity (), "utf-8");
        // 将响应数据放入响应实体类
        responseModel.setStatusCode (response.getStatusLine ().getStatusCode ());
        responseModel.setHeaders (ConvertResponse.headerArrToMap (response.getAllHeaders ()));
        responseModel.setBody (ConvertResponse.resEntityToList (result));
        return responseModel;
    }
}

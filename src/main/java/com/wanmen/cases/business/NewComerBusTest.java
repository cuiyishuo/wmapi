package com.wanmen.cases.business;

import com.wanmen.cases.TestConfig;
import com.wanmen.cases.reward.NewComerTest;
import com.wanmen.cases.users.LoginTest;
import com.wanmen.datas.business.BusinessDataProvider;
import com.wanmen.datas.reward.NewComerDataProvider;
import com.wanmen.mappers.DataBaseInit;
import com.wanmen.model.ResponseModel;
import com.wanmen.model.business.NewComerBusCase;
import com.wanmen.model.reward.NewComerCase;
import com.wanmen.model.users.LoginCase;
import com.wanmen.util.ConfigFile;
import com.wanmen.util.JSONPathMi;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sol
 * @create 2020-08-20  10:21 下午
 */
public class NewComerBusTest {

    LoginTest loginTest = new LoginTest ();
    NewComerTest newComerTest = new NewComerTest();

    @Test(groups = "get_success", dataProvider = "businessProvider", dataProviderClass = BusinessDataProvider.class, description = "新用户成功激活课程业务")
    public void getSuccessBus(NewComerBusCase newComerBusCase) throws IOException{
        // 登录流程
        ResponseModel responseModel = loginTest.getResult (newComerBusCase.getLoginCase ());
        Map responseModelHeaders = responseModel.getHeaders ();
        String authorization = responseModelHeaders.get ("Authorization").toString ();
        // 领取新人礼包流程
        ResponseModel responseModel2 = newComerTest.getResult (newComerBusCase.getNewComerCase (), authorization);
        // 断言
        List<Map<String, Object>> responseModelBody = responseModel2.getBody ();
        String email = JSONPathMi.eval (responseModelBody, "$[0].message");
        String expected = newComerBusCase.getExpected ();
        String emailExpect = JSONPathMi.eval (expected, "$.message");
        Assert.assertEquals (email, emailExpect, newComerBusCase.getCaseName () + "[异常信息提示错误]");
    }
}

package com.wanmen.datas.users;

import com.wanmen.mappers.content.BaseMapper;
import com.wanmen.mappers.users.LoginMapper;
import com.wanmen.model.content.BaseCase;
import com.wanmen.model.users.LoginCase;
import com.wanmen.mappers.DataBaseInit;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据驱动
 *
 * @author sol
 * @create 2020-07-01  5:29 下午
 */
@Slf4j
public class CaseDataProvider {

    @DataProvider(name = "baseProvider")
    public Object[][] baseProvider(Method method) {
        BaseCase[][] data;
        List<BaseCase> cases = new ArrayList<> ();
        try {
            SqlSession session = DataBaseInit.getSqlSession ();
            BaseMapper baseMapper = session.getMapper (BaseMapper.class);
            if (method.getName ().equals ("share")) {
                cases = baseMapper.findBaseCases ("vertify_size");
            }
            data = new BaseCase[cases.size ()][];
            for (int i = 0; i < data.length; i++) {
                data[i] = new BaseCase[1];
                for (int j = 0; j < data[i].length; j++) {
                    data[i][j] = cases.get (i);
                }
            }
            System.out.println (method.getName () + "的case:" + data);
            return data;
        } catch (Exception e) {
            e.printStackTrace ();
            log.error ("数据驱动构造失败");
            return null;
        }
    }

    @DataProvider(name = "loginProvider")
    public Object[][] loginProvider(Method method) {
        LoginCase[][] data;
        List<LoginCase> cases = new ArrayList<> ();
        try {
            SqlSession session = DataBaseInit.getSqlSession ();
            LoginMapper loginMapper = session.getMapper (LoginMapper.class);
            if (method.getName ().equals ("loginTrue")) {
                cases = loginMapper.findLoginCases ("login_success");
            } else if (method.getName ().equals ("loginFalse")) {
                cases = loginMapper.findLoginCases ("login_fail");
            }
            data = new LoginCase[cases.size ()][];
            for (int i = 0; i < data.length; i++) {
                data[i] = new LoginCase[1];
                for (int j = 0; j < data[i].length; j++) {
                    data[i][j] = cases.get (i);
                }
            }
            System.out.println (method.getName () + "的case:" + data);
            return data;
        } catch (Exception e) {
            e.printStackTrace ();
            log.error ("数据驱动构造失败");
            return null;
        }
    }
}

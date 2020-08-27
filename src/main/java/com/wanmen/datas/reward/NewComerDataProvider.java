package com.wanmen.datas.reward;

import com.wanmen.mappers.DataBaseInit;
import com.wanmen.mappers.reward.NewComerMapper;
import com.wanmen.model.reward.NewComerCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sol
 * @create 2020-08-21  10:25 上午
 */
@Slf4j
public class NewComerDataProvider {

    @DataProvider(name = "newComerProvider")
    public Object[][] newComerProvider(Method method) {
        NewComerCase[][] data;
        List<NewComerCase> cases = new ArrayList<> ();
        try {
            SqlSession session = DataBaseInit.getSqlSession ();
            NewComerMapper newComerMapper = session.getMapper (NewComerMapper.class);
            if (method.getName ().equals ("newComer2")) {
                cases = newComerMapper.findNewComerCases ("get_success");
            }
            data = new NewComerCase[cases.size ()][];
            for (int i = 0; i < data.length; i++) {
                data[i] = new NewComerCase[1];
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

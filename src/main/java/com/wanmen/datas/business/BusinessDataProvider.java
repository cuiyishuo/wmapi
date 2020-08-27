package com.wanmen.datas.business;

import com.wanmen.mappers.DataBaseInit;
import com.wanmen.mappers.business.NewComerBusMapper;
import com.wanmen.model.business.NewComerBusCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sol
 * @create 2020-08-24  6:08 下午
 */
@Slf4j
public class BusinessDataProvider {

    @DataProvider(name = "businessProvider")
    public Object[][] businessProvider(Method method) {
        NewComerBusCase[][] data;
        List<NewComerBusCase> cases = new ArrayList<> ();
        try {
            SqlSession session = DataBaseInit.getSqlSession ();
            NewComerBusMapper newComerBusMapper = session.getMapper (NewComerBusMapper.class);
            if (method.getName ().equals ("getSuccessBus")) {
                cases = newComerBusMapper.findNewComerBusCases ("newcomer_bus");
            }
            data = new NewComerBusCase[cases.size ()][];
            for (int i = 0; i < data.length; i++) {
                data[i] = new NewComerBusCase[1];
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

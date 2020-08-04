package com.wanmen.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * 数据库工具类
 *
 * @author sol
 * @create 2020-06-24  4:14 下午
 */
public class DataBaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        //获取配置的资源文件
        Reader reader = Resources.getResourceAsReader ("dataBaseConfig.xml");
        //得到SqlSessionFactory，使用类加载器加载xml文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder ().build (reader);
        //得到sqlsession对象，这个对象就能执行配置文件中的sql语句啦
        SqlSession session = factory.openSession ();

        return session;
    }
}

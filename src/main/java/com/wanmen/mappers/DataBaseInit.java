package com.wanmen.mappers;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DataBaseInit {

    public static SqlSession getSqlSession() {
        //获取配置的资源文件
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader ("dataBaseConfig.xml");
        } catch (IOException e) {
            log.error ("数据库配置文件获取失败");
            e.printStackTrace ();
        }
        //得到SqlSessionFactory，使用类加载器加载xml文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder ().build (reader);
        //得到sqlsession对象，这个对象就能执行配置文件中的sql语句啦
        SqlSession session = factory.openSession ();
        log.info ("数据库会话建立成功！");
        return session;
    }
}

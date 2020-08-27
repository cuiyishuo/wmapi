package com.wanmen.handeler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Java list<>与MySQL String转换器
 *
 * @author sol
 * @create 2020-04-18  09:53
 */
@MappedTypes(List.class)
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class StringAndListHandler extends BaseTypeHandler<List> {


    // --- private methods ---

    /**
     * 数组转String
     */
    public static String arrayToString(List mapList) {
        String maplistStr = "[#]";
        return maplistStr;
    }

    /**
     * String转map数组
     */
    public static List stringToArray(String arrayStr) {
        List list = new ArrayList ();
        JSONArray jsonObject = JSONObject.parseArray (arrayStr);
        for (int i = 0; i < jsonObject.size (); i++) {
            list.add (jsonObject.get (i));
        }
        System.out.println (list);
        return list;
    }

    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        String str = arrayToString (list);
        preparedStatement.setString (i, str);
    }

    public List getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String str = resultSet.getString (columnName);
        return stringToArray (str);
    }

    public List getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String str = resultSet.getString (columnIndex);
        return stringToArray (str);
    }

    public List getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String str = callableStatement.getString (columnIndex);
        return stringToArray (str);
    }
}

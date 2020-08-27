package com.wanmen.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class JSONPathMi {

    /**
     * 封装jsonpath，补货path为空时的异常
     *
     * @param collectionObj 传入一个object，形似json
     * @param path          jsonpath表达式
     */
    public static String eval(Object collectionObj, String path) {
        String jsonStr = "";
        try {
            jsonStr = JSONPath.eval (collectionObj, path).toString ();
        } catch (NullPointerException e) {
            int index = path.lastIndexOf (".");
            String properties = path.substring (index + 1);
            throw new AssertionError ("JSON解析异常，找不到该属性:[" + properties + "],解析的jsonPath：" + path + "，实际返回结果：" + collectionObj.toString ());
        }
        return jsonStr;
    }
}

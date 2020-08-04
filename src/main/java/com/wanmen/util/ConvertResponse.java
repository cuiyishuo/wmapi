package com.wanmen.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应结果转换
 *
 * @author sol
 * @create 2020-07-01  10:21 上午
 */
public class ConvertResponse {
    /**
     * 响应头转map
     *
     * @param headers
     * @return
     */
    public static Map headerArrToMap(Header[] headers) {
        Map<String, String> headersMap = new HashMap<String, String> ();
        for (Header header : headers) {
            headersMap.put (header.getName (), header.getValue ());
        }
        return headersMap;
    }

    /**
     * 响应体转list
     *
     * @param resEntityStr
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> resEntityToList(String resEntityStr) throws IOException {
        List<Map<String, Object>> entityList;
        if (StringUtils.isNotBlank (resEntityStr)) {
            if (!resEntityStr.endsWith ("]"))
                resEntityStr = "[" + resEntityStr + "]";
        }
        entityList = JSON.parseObject (resEntityStr, new TypeReference<List<Map<String, Object>>> () {
        });
        return entityList;
    }
}

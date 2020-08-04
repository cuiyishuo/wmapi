package com.wanmen.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    // 获取资源绑定
    private static ResourceBundle bundle = ResourceBundle.getBundle ("application", Locale.CHINA);

    /**
     * 获取host
     *
     * @return
     */
    public static String getAddress(String uri) {
        String address = bundle.getString ("test.release.url");
        if (address.contains ("beta")) {
            address = address.concat (uri).concat ("?debug=1");
        }
        return address;
    }
}

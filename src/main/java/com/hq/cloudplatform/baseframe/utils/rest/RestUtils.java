package com.hq.cloudplatform.baseframe.utils.rest;

import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.ConfigHelper;

public class RestUtils {
    public static final String CA_HOME = ConfigHelper.getValue("ca.ip");

    public static String getCAServerUrl(String path) {
        StringBuffer url = new StringBuffer();
        url.append("http://").append(Constants.CA_IP)
                .append(":").append(Constants.CA_PORT)
                .append("/").append(Constants.CA_SERVER)
                .append("/restful").append(path);

        return url.toString();
    }
}

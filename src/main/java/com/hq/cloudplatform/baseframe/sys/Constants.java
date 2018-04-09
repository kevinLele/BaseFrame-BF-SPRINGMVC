package com.hq.cloudplatform.baseframe.sys;

import com.hq.cloudplatform.baseframe.utils.ConfigHelper;

public interface Constants {

    String APP_CODE = ConfigHelper.getValue("sys.appCode");

    String CA_IP = ConfigHelper.getValue("ca.ip");

    String CA_PORT = ConfigHelper.getValue("ca.port");

    String CA_SERVER = ConfigHelper.getValue("ca.server");

    String SESSION_KEY_USER = "SESSION_USER";

    interface Caches {

        String DICTIONARY_CACHE = "dictionary";

        String CACHE_NAME = "default";

        String CFG_CACHE_NAME = "config";
    }
}

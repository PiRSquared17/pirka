package org.pirkaengine.mobile.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

}

package org.pirkaengine.mobile.device;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;
import org.pirkaengine.mobile.charset.MobileCharset;

public class KDDIDevice extends Device {

    final HttpServletRequest request;

    public KDDIDevice(HttpServletRequest request) {
        super(Carrier.KDDI, MobileCharset.forKddi());
        this.request = request;
    }

    /**
     * サブスクライバIDを取得する.
     */
    @Override
    public String getGuid() {
        return getUid();
    }

    /**
     * サブスクライバIDを取得する.
     */
    @Override
    public String getUid() {
        return request.getHeader("X-Up-Subno");
    }

}

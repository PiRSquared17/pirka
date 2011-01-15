package org.pirkaengine.mobile.device;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;
import org.pirkaengine.mobile.charset.MobileCharset;

public class SoftbankDevice extends Device {

    final HttpServletRequest request;

    public SoftbankDevice(HttpServletRequest request) {
        super(Carrier.SOFTBANK, MobileCharset.forSoftbank(), "Shift_JIS");
        this.request = request;
    }

    /**
     * ユーザIDを取得する.
     */
    @Override
    public String getGuid() {
        return getUid();
    }

    /**
     * ユーザIDを取得する.
     */
    @Override
    public String getUid() {
        return request.getHeader("x-jphone-uid");
    }

}

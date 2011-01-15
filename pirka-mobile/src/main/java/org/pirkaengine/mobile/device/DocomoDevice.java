package org.pirkaengine.mobile.device;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;
import org.pirkaengine.mobile.charset.MobileCharset;

public class DocomoDevice extends Device {

    final HttpServletRequest request;

    public DocomoDevice(HttpServletRequest request) {
        super(Carrier.DOCOMO, MobileCharset.forDocomo(), "Shift_JIS");
        this.request = request;
    }

    /**
     * 個体識別番号を取得する.
     */
    @Override
    public String getGuid() {
        return request.getHeader("x-dcmguid");
    }

    /**
     * i-mode IDを取得する.
     */
    @Override
    public String getUid() {
        return request.getParameter("uid");
    }

}

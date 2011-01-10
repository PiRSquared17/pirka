package org.pirkaengine.mobile.device;

import java.nio.charset.Charset;

import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;

public class UnkownDevice extends Device {

    public UnkownDevice() {
        super(Carrier.UNKOWN, Charset.defaultCharset());
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.mobile.Device#getGuid()
     */
    @Override
    public String getGuid() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.mobile.Device#getUid()
     */
    @Override
    public String getUid() {
        return null;
    }
}
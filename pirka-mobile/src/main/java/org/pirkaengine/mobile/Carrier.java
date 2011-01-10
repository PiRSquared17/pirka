package org.pirkaengine.mobile;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.mobile.device.KDDIDevice;
import org.pirkaengine.mobile.device.DocomoDevice;
import org.pirkaengine.mobile.device.SoftbankDevice;
import org.pirkaengine.mobile.device.UnkownDevice;
import org.pirkaengine.mobile.util.RequestUtil;

/**
 * Carrierを表す.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public enum Carrier {
    /** Docomo */
    DOCOMO("^DoCoMo.+") {
        @Override
        public Device newDevice(HttpServletRequest req) {
            return new DocomoDevice(req);
        }
    },
    /** KDDI */
    KDDI("^KDDI.+") {
        @Override
        public Device newDevice(HttpServletRequest req) {
            return new KDDIDevice(req);
        }
    },
    /** Softbank */
    SOFTBANK("^(Vodafone|SoftBank|MOT).+") {
        @Override
        public Device newDevice(HttpServletRequest req) {
            return new SoftbankDevice(req);
        }
    },
    /** Unkown */
    UNKOWN("^.*") {
        @Override
        public Device newDevice(HttpServletRequest req) {
            return new UnkownDevice();
        }
    };

    /** carrier pattern */
    final Pattern pattern;

    private Carrier(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * キャリアからデバイスのインスタンスを生成する.
     * @param req HttpServletRequest
     * @return Device
     */
    public abstract Device newDevice(HttpServletRequest req);

    /**
     * {@link HttpServletRequest}を指定して、キャリアを判定する.
     * @param req HttpServletRequest
     * @return Carrier
     */
    public static Carrier detect(HttpServletRequest req) {
        if (req == null) throw new IllegalArgumentException("req == null.");
        String userAgent = RequestUtil.getUserAgent(req);
        if (userAgent == null) return UNKOWN;
        for (Carrier c : Carrier.values()) {
            if (c.pattern.matcher(userAgent).matches()) {
                return c;
            }
        }
        return UNKOWN;
    }
}

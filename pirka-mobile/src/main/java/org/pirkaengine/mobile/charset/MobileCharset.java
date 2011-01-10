package org.pirkaengine.mobile.charset;

import java.nio.charset.Charset;

/**
 * モバイル文字セット
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class MobileCharset {
    private static volatile Charset DOCOMO_SJIS = new DocomoSJIS();
    private static volatile Charset KDDI_SJIS = new KDDISJIS();
    private static volatile Charset SOFTBANK_SJIS = new SoftbankSJIS();

    /**
     * Docomo向けSJISを取得する
     * @return Docomo向けSJIS
     */
    public static Charset forDocomo() {
        return DOCOMO_SJIS;
    }

    /**
     * KDDI向けSJISを取得する
     * @return Docomo向けKDDI
     */
    public static Charset forKddi() {
        return KDDI_SJIS;
    }

    /**
     * Softbank向けSJISを取得する
     * @return Softbank向けSJIS
     */
    public static Charset forSoftbank() {
        return SOFTBANK_SJIS;
    }
}

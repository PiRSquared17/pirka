package org.pirkaengine.mobile;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

/**
 * モバイル・デバイス情報.
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class Device {

    /** key for attribute */
    public static final String KEY = "org.pirkaengine.mobile.Device";
    /** Carrier */
    protected final Carrier carrier;
    /** Charset */
    protected final Charset charset;
    /** Charset */
    protected final String charsetName;

    /**
     * コンストラクタ.
     * @param carrier Carrier
     * @param charset Charset
     */
    public Device(Carrier carrier, Charset charset, String charsetName) {
        this.carrier = carrier;
        this.charset = charset;
        this.charsetName = charsetName;
    }

    /**
     * キャリア情報を取得する.
     * @return キャリア情報
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * キャリアに対応した文字セットを取得する
     * @return 文字セット
     */
    public Charset getCharset() {
        return charset;
    }
    
    /**
     * 文字コードセット名を返す。
     * @return
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * ユーザIDを取得する.
     * @return ユーザID、存在しない場合はnull.
     */
    public abstract String getUid();

    /**
     * 個体識別番号を取得する.
     * @return 個体識別番号、存在しない場合はnull.
     */
    public abstract String getGuid();

    /**
     * {@link HttpServletRequest} を指定し、デバイス情報を取得する
     * @param req HttpServletRequest
     * @return デバイス情報
     */
    public static Device lookup(HttpServletRequest req) {
        return Carrier.detect(req).newDevice(req);
    }
}

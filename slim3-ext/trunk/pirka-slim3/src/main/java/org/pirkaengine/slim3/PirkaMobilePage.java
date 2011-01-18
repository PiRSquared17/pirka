package org.pirkaengine.slim3;

import java.io.IOException;

import org.pirkaengine.core.PirkaException;
import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;
import org.pirkaengine.mobile.EmojiMap;
import org.slim3.controller.Navigation;

/**
 * 
 * <p></p>
 * @author shuji.w6e
 * @since 0.2.0
 */
public abstract class PirkaMobilePage extends PirkaPage {

    /** information of mobile device */
    protected Device device;

    /*
     * (non-Javadoc)
     * @see scenic3.ScenicPage#setUp()
     */
    @Override
    protected void setUp() {
        device = Device.lookup(request);
        assert device != null;
        viewModel("emoji", new EmojiMap());
        super.setUp();
    }

    /**
     * デバイス情報を取得する
     * @return デバイス情報
     */
    protected Device getDevice() {
        return device;
    }

    /**
     * キャリア情報を取得する
     * @return キャリア情報
     */
    protected Carrier getCarrier() {
        return device.getCarrier();
    }

    /**
     * キャリアがドコモの場合にtrueを返す.
     * @return キャリアがドコモの場合にtrue
     */
    protected boolean isDocomo() {
        return getCarrier() == Carrier.DOCOMO;
    }

    /**
     * キャリアがKDDIの場合にtrueを返す.
     * @return キャリアがKDDIの場合にtrue
     */
    protected boolean isKddi() {
        return getCarrier() == Carrier.KDDI;
    }

    /**
     * キャリアがソフトバンクの場合にtrueを返す.
     * @return キャリアがソフトバンクの場合にtrue
     */
    protected boolean isSoftbank() {
        return getCarrier() == Carrier.SOFTBANK;
    }

    /**
     * 個人識別情報を取得する
     * @return 個人識別情報
     */
    protected String getUid() {
        return device.getUid();
    }

    /**
     * 端末識別情報を取得する
     * @return 端末識別情報
     */
    protected String getGuid() {
        return device.getGuid();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.slim3.PirkaPage#render(java.lang.String)
     */
    @Override
    protected Navigation render(String templateName) throws PirkaException, IOException {
        if (templateName == null) throw new IllegalArgumentException("templateName is null.");
        assert device != null;
        if (device.getCarrier() != Carrier.UNKOWN) {
            if (device.getCarrier() == Carrier.DOCOMO) {
                PirkaRenderer.setUpResponse(response, viewModel, "application/xhtml+xml", device.getCharsetName());
            } else {
                PirkaRenderer.setUpResponse(response, viewModel, "text/html", device.getCharsetName());
            }
            return PirkaRenderer.render(request, response, templateName, viewModel, device.getCharset());
        }
        return super.render(templateName);
    }
}

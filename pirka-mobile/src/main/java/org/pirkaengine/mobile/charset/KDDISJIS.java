package org.pirkaengine.mobile.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.pirkaengine.mobile.Emoji;

/**
 * Charset for KDDI
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
class KDDISJIS extends Charset {

    KDDISJIS() {
        super("MS932-kddi", new String[0]);
    }

    @Override
    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof KDDISJIS);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    static class Encoder extends MS932Encoder {

        protected Encoder(KDDISJIS charset) {
            super(charset);
        }

        @Override
        byte[] getCarrierEmojiCode(Emoji emoji) {
            return emoji.toKddiCode();
        }
    }

    static class Decoder extends MS932Decoder {

        protected Decoder(KDDISJIS chaeset) {
            super(chaeset);
        }

    }

}

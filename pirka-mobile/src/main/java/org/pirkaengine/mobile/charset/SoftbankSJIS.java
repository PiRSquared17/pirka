package org.pirkaengine.mobile.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.pirkaengine.mobile.Emoji;

class SoftbankSJIS extends Charset {

    SoftbankSJIS() {
        super("MS932-softbank", new String[0]);
    }

    @Override
    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof SoftbankSJIS);
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

        protected Encoder(SoftbankSJIS charset) {
            super(charset);
        }

        @Override
        byte[] getCarrierEmojiCode(Emoji emoji) {
            return emoji.toSoftbankCode();
        }
    }

    static class Decoder extends MS932Decoder {

        protected Decoder(SoftbankSJIS chaeset) {
            super(chaeset);
        }

    }

}

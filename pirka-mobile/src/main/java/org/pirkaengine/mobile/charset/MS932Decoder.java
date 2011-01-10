package org.pirkaengine.mobile.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

import org.pirkaengine.mobile.charset.codemap.CodeMap;

/**
 * Decode bytes to char.
 * <p>
 * ASCII:  0x00 - 0x80
 * 
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
abstract class MS932Decoder extends CharsetDecoder {

    protected MS932Decoder(Charset chaeset) {
        super(chaeset, 0.5f, 1.0f);
    }

    /*
     * (non-Javadoc)
     * @see java.nio.charset.CharsetDecoder#decodeLoop(java.nio.ByteBuffer, java.nio.CharBuffer)
     */
    @Override
    protected final CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
        int pos = in.position();
        try {
            while (in.hasRemaining()) {
                if (out.remaining() == 0) return CoderResult.OVERFLOW;
                int b1 = in.get() & 0xFF;
                if (b1 <= 0x80 || (0xA0 <= b1 && b1 <= 0xDF)) {
                    out.put((char) CodeMap.decode(b1));
                    pos++;
                } else if (in.hasRemaining()) {
                    int b2 = in.get() & 0xFF;
                    int b = (b1 << 8) + b2;
                    out.put((char) CodeMap.decode(b));
                    pos += 2;
                } else {
                    assert false;
                }
            }
        } finally {
            in.position(pos);
        }
        return CoderResult.UNDERFLOW;
    }

}

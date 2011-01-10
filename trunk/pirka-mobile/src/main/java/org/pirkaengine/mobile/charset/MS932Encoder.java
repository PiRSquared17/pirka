package org.pirkaengine.mobile.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import org.pirkaengine.mobile.Emoji;
import org.pirkaengine.mobile.charset.codemap.CodeMap;

/**
 * Encode cahr to bytes.
 * <p>
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
abstract class MS932Encoder extends CharsetEncoder {

    MS932Encoder(Charset charset) {
        super(charset, 2.0f, 4.0f);
    }

    /*
     * (non-Javadoc)
     * @see java.nio.charset.CharsetEncoder#encodeLoop(java.nio.CharBuffer, java.nio.ByteBuffer)
     */
    @Override
    protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
        int pos = in.position();
        try {
            while (in.hasRemaining()) {
                if (out.remaining() < 2) return CoderResult.OVERFLOW;
                char c = in.get();
                if (Character.isHighSurrogate(c)) {
                    // with surrogate
                    char low = in.get();
                    int codePoint = Character.toCodePoint(c, low);
                    Emoji emoji = Emoji.charOf(codePoint);
                    out.put(getEmojiCode(emoji));
                    pos += 2;
                } else if (c == 0x0023 || (0x0030 <= c && c <= 0x0039)) {
                    // 2文字で絵文字に変換される特殊文字
                    char c2 = in.get();
                    if (c2 == 0x20E3) {
                        out.put(getCarrierEmojiCode(Emoji.capsCharOf(c)));
                    } else {
                        out(c, out);
                        out(c2, out);
                    }
                    pos += 2;
                } else {
                    Emoji emoji = Emoji.charOf(c);
                    if (emoji != null) {
                        // emoji
                        out.put(getEmojiCode(emoji));
                    } else {
                        // その他
                        out(c, out);
                    }
                    pos++;
                }
            }
        } finally {
            in.position(pos);
        }
        return CoderResult.UNDERFLOW;
    }

    private void out(char c, ByteBuffer out) {
        int b = CodeMap.encode(c);
        if (b < 0xFF) {
            out.put((byte) b);
        } else {
            out.put((byte) ((b & 0xFF00) >> 8));
            out.put((byte) (b & 0x00FF));
        }
    }

    byte[] getEmojiCode(Emoji emoji) {
        byte[] b = getCarrierEmojiCode(emoji);
        if (b == null) { // using fallback text
            return emoji.fallbackText().getBytes(super.charset());
        }
        return b;
    }

    abstract byte[] getCarrierEmojiCode(Emoji emoji);

}

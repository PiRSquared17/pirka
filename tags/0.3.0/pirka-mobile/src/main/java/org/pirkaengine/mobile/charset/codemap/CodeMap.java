package org.pirkaengine.mobile.charset.codemap;

/**
 * Code map MS932 to UTF-8.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class CodeMap {

    /**
     * charからbyteにエンコードする.
     * @param c
     * @return
     */
    public static int encode(int c) {
        if (0x0000 <= c && c <= 0x451) {
            return EncodeMap0x0000.DB[c];
        } else if (0x2010 <= c && c <= 0x266F) {
            return EncodeMap0x2010.DB[c - 0x2010];
        } else if (0x3000 <= c && c <= 0x33CD) {
            return EncodeMap0x3000.DB[c - 0x3000];
        } else if (0x4E00 <= c && c <= 0x5FFF) {
            return EncodeMap0x4E00.DB[c - 0x4E00];
        } else if (0x6000 <= c && c <= 0x7FFF) {
            return EncodeMap0x6000.DB[c - 0x6000];
        } else if (0x8000 <= c && c <= 0x9FA0) {
            return EncodeMap0x8000.DB[c - 0x8000];
        } else if (0xE000 <= c && c <= 0xE757) {
            return EncodeMap0xE000.DB[c - 0xE000];
        } else if (0xF8F0 <= c && c <= 0xFA2D) {
            return EncodeMap0xF8F0.DB[c - 0xF8F0];
        } else if (0xFF01 <= c && c <= 0xFFE5) {
            return EncodeMap0xFF01.DB[c - 0xFF01];
        } else {
            return 0x3F;
        }
    }

    /**
     * byteからcharにデコードする
     * @param b
     * @return
     */
    public static char decode(int b) {
        if (0x00 <= b && b <= 0xFF) {
            return (char) DecodeMap0x0000.DB[b];
        } else if (0x8140 <= b && b <= 0x9FFC) {
            return (char) DecodeMap0x8140.DB[b - 0x8140];
        } else if (0xE040 <= b && b <= 0xFC4B) {
            return (char) DecodeMap0xE040.DB[b - 0xE040];
        } else {
            return 0xFFFD;
        }
    }
}

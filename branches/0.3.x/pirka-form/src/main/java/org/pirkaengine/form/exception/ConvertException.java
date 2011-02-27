package org.pirkaengine.form.exception;

/**
 * 変換に失敗した場合にthrowされる例外。
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ConvertException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String format;

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param format フォーマット
     */
    public ConvertException(String format) {
        this(null, format);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause 原因となった例外
     */
    public ConvertException(Throwable cause) {
        this(cause, null);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause 原因となった例外
     * @param format フォーマット
     */
    public ConvertException(Throwable cause, String format) {
        super(cause);
        this.format = format;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

}

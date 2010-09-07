package org.pirkaengine.core.parser;

import org.pirkaengine.core.PirkaException;

/**
 * Exception when parse.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ParseException extends PirkaException {
    private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ.
     * @param message
     * @param cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * コンストラクタ.
     * @param message
     */
    public ParseException(String message) {
        super(message);
    }
    
    /**
     * コンストラクタ.
     * @param cause
     */
    public ParseException(Throwable cause) {
        super(cause);
    }
}

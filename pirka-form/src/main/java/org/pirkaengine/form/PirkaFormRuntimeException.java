package org.pirkaengine.form;

/**
 * Formの例外基底クラス
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaFormRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * 原因となった例外とメッセージを指定してインスタンスを生成する.
	 * 
	 * @param message
	 * @param cause
	 */
	public PirkaFormRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * メッセージを指定してインスタンスを生成する.
	 * 
	 * @param message
	 */
	public PirkaFormRuntimeException(String message) {
		super(message);
	}

	/**
	 * 原因となった例外を指定してインスタンスを生成する.
	 * 
	 * @param cause
	 */
	public PirkaFormRuntimeException(Throwable cause) {
		super(cause);
	}

}

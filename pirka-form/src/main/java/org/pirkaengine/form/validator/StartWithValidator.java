package org.pirkaengine.form.validator;

import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 特定の文字列で開始されているかを判定するValidator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class StartWithValidator extends ValidatorBase<String> {

    private final String prefix;

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param prefix プレフィックス
     */
    public StartWithValidator(String prefix) {
        if (prefix == null || prefix.isEmpty()) throw new ValidatorFormatException("Illegal prefix: " + prefix);
        this.prefix = prefix;
    }

    @Override
    protected boolean isValid(String value) {
        if (value == null) throw new IllegalArgumentException("value is null.");
        return value.startsWith(prefix);
    }

    @Override
    public String getMessageKey() {
        return "validator.startWith";
    }

    @Override
    protected Object[] getMessageArgs(String name, String value) {
        return new Object[] { name, prefix };
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StartWithValidator other = (StartWithValidator) obj;
        if (prefix == null) {
            if (other.prefix != null) return false;
        } else if (!prefix.equals(other.prefix)) return false;
        return true;
    }

}

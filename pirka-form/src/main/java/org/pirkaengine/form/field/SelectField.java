package org.pirkaengine.form.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pirkaengine.form.exception.ConvertException;


/**
 * セレクトフィールド.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class SelectField extends BaseField<String> {

    private final List<String> options;

    /**
     * オプションの文字列の配列を指定して初期化する
     */
    public SelectField() {
        this(new ArrayList<String>());
    }
    
    /**
     * オプションの文字列の配列を指定して初期化する。
     * @param options オプション
     */
    public SelectField(String[] options) {
        if (options == null) throw new IllegalArgumentException("options == null");
        this.options = Arrays.asList(options);
        if (0 < options.length) setValue(options[0]);
    }

    /**
     * オプションの文字列の配列を指定して初期化する。
     * @param options オプション
     */
    public SelectField(List<String> options) {
        if (options == null) throw new IllegalArgumentException("options == null");
        this.options = options;
        if (!options.isEmpty()) setValue(options.get(0));
    }
    
    /**
     * オプションを再設定する。
     * @param newOptions オプション
     */
    public void setOptions(List<String> newOptions) {
        this.options.clear();
        this.options.addAll(newOptions);
    }
    
    /**
     * オプションを追加する。
     * @param option オプション
     */
    public void addOption(String option) {
        this.options.add(option);
    }
    
    /**
     * オプション項目を取得する.
     * @return オプション項目
     */
    public List<String> options() {
        return options;
    }
    
    /**
     * バリュー項目を取得する.
     * @return バリュー項目
     */
    public List<String> values() {
        return options;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.form.field.BaseField#convert(java.lang.String)
     */
    @Override
    protected String convert(String text) {
        if (text == null || text.isEmpty()) return null;
        if (!options.isEmpty() && !options.contains(text)) throw new ConvertException(options.toString());
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(String value) {
        return value;
    }

}

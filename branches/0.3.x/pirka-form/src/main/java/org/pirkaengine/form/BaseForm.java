package org.pirkaengine.form;

import java.util.LinkedList;
import java.util.List;

import org.pirkaengine.form.field.BaseField;

/**
 * フォームの基底クラス.
 * <p>
 * 汎用的なフォームを作るにはこのクラスを基底クラスとし、
 * フィールドを宣言していく。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @param <E>
 */
public abstract class BaseForm<E> {
    /** フィールドリスト */
    protected final List<BaseField<?>> fields = new LinkedList<BaseField<?>>();

    /**
     * 妥当性チェックを行う.
     * @return 全てのフィールドのクリーニングが成功した場合true
     */
    public boolean validate() {
        boolean isValidate = true;
        for (BaseField<?> field : this.fields) {
            isValidate = field.clean() && isValidate;
        }
        return isValidate;
    }

    /**
     * フォームの情報をエンティティへ反映する.
     * @param entity エンティティ
     */
    abstract public void setupEntity(E entity);

    /**
     * エンティティのデータをフォームに反映する.
     * @param entity エンティティ
     */
    abstract public void fill(E entity);

    /**
     * newFormでインスタンスが生成される時、事後処理として実行される。
     * <p>
     * 実装クラスではこのメソッドをオーバーライドする事で、
     * 動的な初期値を設定することができる。
     * </p>
     */
    protected void postinit() {
    }
}

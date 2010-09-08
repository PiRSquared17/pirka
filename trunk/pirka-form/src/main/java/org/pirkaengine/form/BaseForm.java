package org.pirkaengine.form;

import java.lang.reflect.Field;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.form.field.BaseField;
import org.pirkaengine.form.field.BooleanField;

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
    protected final LinkedList<BaseField<?>> fields = new LinkedList<BaseField<?>>();

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

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param <T>
     * @param formClass
     * @param request
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass, HttpServletRequest request) {
        T form = newForm(formClass, false);
        for (BaseField<?> field : form.fields) {
            if (BooleanField.class.isInstance(field) && request.getParameter(field.name) == null) {
                // Boolean Field ＝ Check Boxの場合、未チェックはパラメータで設定されない
                continue;
            }
            field.setRawText(request.getParameter(field.name));
        }
        return form;
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param <T>
     * @param <E>
     * @param formClass
     * @param entity
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<E>, E> T newForm(Class<T> formClass, E entity) {
        T form = newForm(formClass, false);
        form.fill(entity);
        return form;
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param <T>
     * @param formClass
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass) {
        return newForm(formClass, true);
    }

    static <T extends BaseForm<?>> T newForm(Class<T> formClass, boolean doPostinit) {
        if (formClass == null) throw new IllegalArgumentException();
        try {
            T form = formClass.newInstance();
            for (Field field : formClass.getFields()) {
                if (!BaseField.class.isAssignableFrom(field.getType())) continue;
                BaseField<?> fieldBase = (BaseField<?>) field.get(form);
                if (fieldBase == null) throw new IllegalArgumentException(); // TODO
                String name = field.getName();
                fieldBase.apply(name, field.getDeclaredAnnotations());
                form.fields.add(fieldBase);
            }
            if (doPostinit) form.postinit();
            return form;
        } catch (InstantiationException e) {
            throw new IllegalFormFormatException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalFormFormatException(e);
        }
    }
}

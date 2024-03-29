package org.pirkaengine.form;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.pirkaengine.form.exception.IllegalFormFormatException;
import org.pirkaengine.form.field.BaseField;
import org.pirkaengine.form.field.BooleanField;

/**
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class Forms {

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param request リクエスト
     * @param <T> フォーム型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass, HttpServletRequest request) {
        return newForm(formClass, request, new ResourceBundleFormMessage());
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param request リクエスト
     * @param formMessage フォームメッセージ
     * @param <T> フォーム型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass, HttpServletRequest request,
            FormMessage formMessage) {
        T form = newForm(formClass, formMessage, false);
        for (BaseField<?> field : form.fields) {
            if (BooleanField.class.isInstance(field) && request.getParameter(field.getName()) == null) {
                // Boolean Field ＝ Check Boxの場合、未チェックはパラメータで設定されない
                continue;
            }
            field.setRawText(request.getParameter(field.getName()));
        }
        return form;
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param entity エンティティ
     * @param <T> フォーム型
     * @param <E> フォームのエンティティ型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<E>, E> T newForm(Class<T> formClass, E entity) {
        return newForm(formClass, entity, new ResourceBundleFormMessage());
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param entity エンティティ
     * @param formMessage フォームメッセージ
     * @param <T> フォーム型
     * @param <E> フォームのエンティティ型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<E>, E> T newForm(Class<T> formClass, E entity, FormMessage formMessage) {
        T form = newForm(formClass, formMessage, false);
        form.fill(entity);
        return form;
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param <T> フォーム型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass) {
        return newForm(formClass, new ResourceBundleFormMessage());
    }

    /**
     * フォームのインスタンスを生成する.
     * <p>
     * フォームに定義されたアノテーションから、
     * 各フィールドの設定を行う。
     * </p>
     * @param formClass フォームのclassインスタンス
     * @param formMessage フォームメッセージ
     * @param <T> フォーム型
     * @return フォームのインスタンス
     */
    public static <T extends BaseForm<?>> T newForm(Class<T> formClass, FormMessage formMessage) {
        return newForm(formClass, formMessage, true);
    }

    static <T extends BaseForm<?>> T newForm(Class<T> formClass, FormMessage formMessage, boolean doPostinit) {
        if (formClass == null) throw new IllegalArgumentException("formClass is null.");
        if (formMessage == null) throw new IllegalArgumentException("formMessage is null.");
        try {
            T form = formClass.newInstance();
            for (Field field : formClass.getFields()) {
                if (!BaseField.class.isAssignableFrom(field.getType())) continue;
                BaseField<?> fieldBase = (BaseField<?>) field.get(form);
                assert fieldBase != null;
                String name = field.getName();
                fieldBase.apply(name, formMessage, field.getDeclaredAnnotations());
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

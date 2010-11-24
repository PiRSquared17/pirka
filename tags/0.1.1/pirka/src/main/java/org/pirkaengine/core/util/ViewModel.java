package org.pirkaengine.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.pirkaengine.core.ModelDeficientPropertyException;
import org.pirkaengine.core.Template;


/**
 * ViewModelへModelオブジェクトを反映するユーティリティクラス.
 * <p>
 * {@code Map<String, Object> viewModel = ViewModel.create(template, this); }<br />
 * {@code template.generate(viewModel); }
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ViewModel {

    /**
     * テンプレートのViewModelにモデルのプロパティを反映する.
     * @param template テンプレート
     * @param model モデル
     * @return ViewModel
     * @throws ModelDeficientPropertyException プロパティが不足している場合
     */
    public static Map<String, Object> create(Template template, Object model) throws ModelDeficientPropertyException {
        return (new Builder(template, model)).build();
    }

    private ViewModel() {
        throw new AssertionError("cant create instance.");
    }

    private static class Builder {
        private final Map<String, Object> viewModel;
        private final Object model;

        Builder(Template template, Object model) {
            this.viewModel = template.createViewModel();
            this.model = model;
        }

        Map<String, Object> build() throws ModelDeficientPropertyException {
            Class<?> modelClass = model.getClass();
            for (String key : viewModel.keySet()) {
                Object value = viewModel.get(key);
                if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<Object, Object> nest = (Map<Object, Object>) value;
                    setValues(nest, getValue(modelClass, model, key));
                } else {
                    viewModel.put(key, getValue(modelClass, model, key));
                }
            }
            return viewModel;
        }

        private void setValues(Map<Object, Object> map, Object object) throws ModelDeficientPropertyException {
            Class<?> modelClass = object.getClass();
            for (Object keyObj : map.keySet()) {
                String key = keyObj.toString();
                Object value = map.get(key);
                if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<Object, Object> nest = (Map<Object, Object>) value;
                    setValues(nest, getValue(modelClass, object, key));
                } else {
                    map.put(keyObj, getValue(modelClass, object, key));
                }
            }
        }

        private Object getValue(Class<?> modelClass, Object model, String key) throws ModelDeficientPropertyException {
            try {
                Field field = modelClass.getField(key);
                if (field != null && Modifier.isPublic(field.getModifiers())) {
                    return field.get(model);
                }
            } catch (NoSuchFieldException e) {
                // do nothing
            } catch (IllegalAccessException e) {
                throw new ModelDeficientPropertyException("field: " + key, e);
            } catch (RuntimeException e) {
                throw new ModelDeficientPropertyException("field: " + key, e);
            }
            String getterMethod = "get" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
            try {
                Method getter = modelClass.getMethod(getterMethod);
                if (getter != null && Modifier.isPublic(getter.getModifiers())) {
                    return getter.invoke(model);
                }
            } catch (NoSuchMethodException e) {
                // do nothing
            } catch (InvocationTargetException e) {
                throw new ModelDeficientPropertyException("getter method: " + getterMethod, e);
            } catch (IllegalAccessException e) {
                throw new ModelDeficientPropertyException("getter method: " + getterMethod, e);
            } catch (RuntimeException e) {
                throw new ModelDeficientPropertyException("getter method: " + getterMethod, e);
            }
            try {
                Method method = modelClass.getMethod(key);
                if (method != null && Modifier.isPublic(method.getModifiers())) {
                    return method.invoke(model);
                }
            } catch (NoSuchMethodException e) {
                // do nothing
            } catch (InvocationTargetException e) {
                throw new ModelDeficientPropertyException("method: " + key, e);
            } catch (IllegalAccessException e) {
                throw new ModelDeficientPropertyException("method: " + key, e);
            } catch (RuntimeException e) {
                throw new ModelDeficientPropertyException("method: " + key, e);
            }
            throw new ModelDeficientPropertyException("key: " + key);
        }
    }

}

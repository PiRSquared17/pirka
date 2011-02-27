package org.pirkaengine.mobile;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 絵文字をMapとして取得するためのクラス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EmojiMap implements Map<String, Emoji> {
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public Emoji get(Object key) {
        if (key == null) throw new NullPointerException("key == null.");
        return Emoji.valueOf((String) key);
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return Emoji.values().length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) throw new NullPointerException("key == null.");
        return Emoji.valueOf((String) key) != null;
    }

    @Override
    public Collection<Emoji> values() {
        return Arrays.asList(Emoji.values());
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public Emoji put(String key, Emoji value) {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public Emoji remove(Object key) {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Emoji> m) {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException("not support.");
    }

    @Override
    public Set<java.util.Map.Entry<String, Emoji>> entrySet() {
        throw new UnsupportedOperationException("not support.");
    }

}

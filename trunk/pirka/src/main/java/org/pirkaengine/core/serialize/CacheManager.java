package org.pirkaengine.core.serialize;

import static org.pirkaengine.core.util.Logger.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.pirkaengine.core.Template;



/**
 * キャッシュ管理クラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class CacheManager {

    /** CacheManager */
    private static final CacheManager INSTANCE = new CacheManager();

    private final CacheSerializer serializer;
    private final CacheDeserializer deserializer;
    private final Lock lock = new ReentrantLock();
    private final ConcurrentMap<String, CacheLock> locks = new ConcurrentHashMap<String, CacheLock>();

    private static class CacheLock {
        private final Lock lock = new ReentrantLock();
        private final AtomicInteger count = new AtomicInteger(0);

        private int lock() {
            this.lock.lock();
            return this.count.incrementAndGet();
        }

        private int unlock() {
            this.lock.unlock();
            return this.count.decrementAndGet();
        }
    }

    /**
     * インスタンスを取得する.
     * @return インスタンス
     */
    public static CacheManager getInstance() {
        return INSTANCE;
    }

    /**
     * コンストラクタ.
     */
    protected CacheManager() {
        this(new CacheSerializerImpl(), new CacheDeserializerImpl());
    }
    
    /**
     * コンストラクタ.
     * @param serializer
     * @param deserializer
     */
    protected CacheManager(CacheSerializer serializer, CacheDeserializer deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    /**
     * テンプレートをシリアライズ化して、保存する。
     * @param template
     * @throws SerializeException
     */
    public void serialize(Template template) throws SerializeException {
        if (template == null) throw new IllegalArgumentException("template == null");
        if (isDebugEnabled()) debug("start serialize: " + template);
        this.lock(template.getName());
        try {
            this.serializer.serialize(template);
        } catch (IOException e) {
            throw new SerializeException("serialize error.", e);
        } finally {
            this.unlock(template.getName());
            if (isDebugEnabled()) debug("end serialize: " + template);
        }
    }

    /**
     * 保存されたテンプレートをデシリアライズする。
     * @param templateName
     * @return デシリアライズされたテンプレート
     * @throws SerializeException
     */
    public Template deserialize(String templateName) throws SerializeException {
        if (templateName == null) throw new IllegalArgumentException("templateName == null");
        if (isDebugEnabled()) debug("start deserialize: " + templateName);
        this.lock(templateName);
        try {
            return this.deserializer.deserialize(templateName);
        } catch (ClassNotFoundException e) {
            throw new SerializeException("deserialize error.", e);
        } catch (IOException e) {
            throw new SerializeException("deserialize error.", e);
        } finally {
            this.unlock(templateName);
            if (isDebugEnabled()) debug("end deserialize: " + templateName);
        }
    }

    private void lock(String templateName) {
        if (isDebugEnabled()) debug("lock: " + templateName);
        lock.lock();
        try {
            CacheLock tmplLock = locks.get(templateName);
            if (tmplLock == null) {
                tmplLock = new CacheLock();
                locks.put(templateName, tmplLock);
            }
            tmplLock.lock();
        } finally {
            if (isDebugEnabled()) debug("unlock: " + templateName);
            lock.unlock();
        }
    }

    private void unlock(String templateName) {
        if (isDebugEnabled()) debug("unlock: " + templateName);
        CacheLock tmplLock = locks.get(templateName);
        if (tmplLock != null) tmplLock.unlock();
    }
}

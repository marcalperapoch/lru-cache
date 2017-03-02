package com.perapoch.cache;

import com.perapoch.cache.concurrent.ConcurrentCache;
import com.perapoch.cache.lru.LRUCache;

/**
 * Created by marcal.perapoch on 02/03/2017.
 */
public interface CacheFactory {

    static <K,V> Cache<K, V> createConcurrentLRUCache(int maxElements) {
        return new ConcurrentCache<>(createLRUCache(maxElements));
    }

    static <K,V> Cache<K, V> createLRUCache(int maxElements) {
        return new LRUCache<>(maxElements);
    }
}

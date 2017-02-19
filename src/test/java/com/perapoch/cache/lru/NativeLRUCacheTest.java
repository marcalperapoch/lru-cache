package com.perapoch.cache.lru;

import com.perapoch.cache.Cache;

/**
 * Created by marcalperapochamado on 19/02/17.
 */
public class NativeLRUCacheTest extends CacheTest {

    @Override
    protected Cache<Integer, String> provideCacheImpl(int capacity) {
        return new NativeLRUCache<>(capacity);
    }
}

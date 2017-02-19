package com.perapoch.cache.lru;

import com.perapoch.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by marcalperapochamado on 19/02/17.
 */
public class NativeLRUCache<K, V> implements Cache<K, V> {

    private final LinkedHashMapCache<K, V> cache;

    public NativeLRUCache(int capacity) {
        this.cache = new LinkedHashMapCache<K, V>(capacity);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    private static class LinkedHashMapCache<K, V> extends LinkedHashMap<K, V> {

        private final int capacity;

        private LinkedHashMapCache(int capacity) {
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }
}

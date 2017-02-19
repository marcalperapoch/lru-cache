package com.perapoch.cache.concurrent;

import com.perapoch.cache.Cache;

import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.perapoch.cache.concurrent.Guard.guardedBy;

/**
 * Created by marcalperapochamado on 19/02/17.
 */
public class ConcurrentCache<K, V> implements Cache<K, V> {

    private final Cache cache;
    private final ReadWriteLock readWriteLock;

    public ConcurrentCache(Cache<K, V> cache) {
        this.cache = cache;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public Optional<V> get(K key) {
        return guardedBy(readWriteLock).executeWrite(() -> cache.get(key));
    }

    @Override
    public void put(K key, V value) {
        guardedBy(readWriteLock).executeWrite(() -> cache.put(key, value));
    }

    @Override
    public boolean containsKey(K key) {
        return guardedBy(readWriteLock).executeRead(() -> cache.containsKey(key));
    }

    @Override
    public int size() {
        return guardedBy(readWriteLock).executeRead(() -> cache.size());
    }


}

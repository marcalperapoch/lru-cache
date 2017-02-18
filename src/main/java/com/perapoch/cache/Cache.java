package com.perapoch.cache;


import java.util.Optional;

/**
 * Created by marcalperapochamado on 18/02/17.
 */
public interface Cache<K, V> {

    Optional<V> get(K key);

    void put(K key, V value);

    void remove(K key);

    boolean containsKey(K key);

}

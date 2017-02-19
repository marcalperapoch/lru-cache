package com.perapoch.cache.lru;

import com.perapoch.cache.Cache;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by marcalperapochamado on 18/02/17.
 */
public class LRUCacheTest extends CacheTest {

    private static final int CAPACITY = 5;

    @Override
    protected Cache<Integer, String> provideCacheImpl(int capacity) {
        return new LRUCache<>(capacity);
    }

    @Test
    public void get_shouldModifyLastRecentUsageToBecomeThatKey() {
        cache.put(4, "four");
        cache.put(1, "one");
        cache.put(6, "six");
        assertThat("Initially last used is 6", ((LRUCache) cache).getLastRecentKey().get(), is(6));
        cache.get(1);
        assertThat("After get(1) last used is 1", ((LRUCache) cache).getLastRecentKey().get(), is(1));
    }

    @Test
    public void getLastRecentKey_shouldReturnAnEmptyOptionalIfNoCachedElements() {
        assertThat(((LRUCache) cache).getLastRecentKey().isPresent(), is(false));
    }

    @Test
    public void put_shouldUpdateLastUsedElementIfKeyAlreadyExists() {
        IntStream.rangeClosed(0, 5).forEach(num -> cache.put(num, String.valueOf(num)));
        assertThat("cache is full", cache.size(), is(CAPACITY));
        cache.put(3, "three");
        assertThat("3 is the last used key", ((LRUCache) cache).getLastRecentKey().get(), is(3));
    }
}

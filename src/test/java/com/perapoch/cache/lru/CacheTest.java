package com.perapoch.cache.lru;

import com.perapoch.cache.Cache;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marcalperapochamado on 19/02/17.
 */
public abstract class CacheTest {

    private static final int CAPACITY = 5;

    // SUT
    Cache<Integer, String> cache;

    @Before
    public void init() {
        cache = provideCacheImpl(CAPACITY);
    }

    protected abstract Cache<Integer,String> provideCacheImpl(int capacity);

    @Test
    public void size_shouldBeZeroIfNoElements() {
        assertThat(cache.size(), is(0));
    }

    @Test
    public void put_shouldIncreaseTheSizeByOne() {
        cache.put(1, "one");
        assertThat(cache.size(), is(1));
    }

    @Test
    public void containsKey_shouldReturnTrueIfTheKeyHasBeenInsertedBefore() {
        cache.put(68, "sixty-eight");
        assertThat(cache.containsKey(68), is(true));
    }

    @Test
    public void containsKey_shouldReturnFalseIfTheKeyHasNotBeenInsertedBefore() {
        assertThat(cache.containsKey(68), is(false));
    }

    @Test
    public void put_shouldRemoveLastUsedElementIfCapacityLimitReached() {
        IntStream.rangeClosed(0, 5).forEach(num -> cache.put(num, String.valueOf(num)));
        assertThat("first element has been removed", cache.containsKey(0), is(false));
        assertThat("second element still present", cache.containsKey(1), is(true));
        assertThat("last element still present", cache.containsKey(5), is(true));
    }

    @Test
    public void get_shouldReturnAnEmptyOptionalIfNoElementFoundForTheGivenKey() {
        final Optional<String> result = cache.get(5);
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void get_shouldReturnAnOptionalContainingTheValueIfKeyIsPresent() {
        cache.put(90, "ninety");
        assertThat(cache.get(90).get(), is("ninety"));
    }

    @Test
    public void put_shouldNotRemoveLastUsedElementIfKeyAlreadyExists() {
        IntStream.rangeClosed(0, 5).forEach(num -> cache.put(num, String.valueOf(num)));
        assertThat("cache is full", cache.size(), is(CAPACITY));
        cache.put(3, "three");
        assertThat("cache still full", cache.size(), is(CAPACITY));
    }

}

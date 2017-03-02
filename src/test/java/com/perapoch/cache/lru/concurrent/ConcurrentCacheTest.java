package com.perapoch.cache.lru.concurrent;

import com.perapoch.cache.Cache;
import com.perapoch.cache.CacheFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marcal.perapoch on 02/03/2017.
 */
public class ConcurrentCacheTest {

    private static final int MAX_ELEMENTS = 100;
    // SUT
    private Cache<Integer, String> cache;
    private CountDownLatch tasksCounter;

    @Before
    public void init() {
        cache = CacheFactory.createConcurrentLRUCache(MAX_ELEMENTS);
        tasksCounter = new CountDownLatch(MAX_ELEMENTS);
    }

    @Test
    public void multipleThreads_canSetValuesWithoutLoosingThem() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            IntStream
                    .range(1, MAX_ELEMENTS + 1)
                    .forEach(id -> executorService.submit(new NumberProducer(id, String.format("Producer{%d}", id), cache, tasksCounter)));
            tasksCounter.await();
        } finally {
            executorService.shutdown();
        }
        assertThat("all threads have set their values", cache.size(), is(MAX_ELEMENTS));
        IntStream
                .range(1, MAX_ELEMENTS + 1)
                .forEach(id -> {
                    final String expectedValue = String.format("Producer{%d}", id);
                    assertThat(String.format("Element %d is present with value %s", id, expectedValue), cache.get(id).get(), is(expectedValue));
                });
    }


    private static class NumberProducer implements Runnable {

        private final int id;
        private final String name;
        private final Cache<Integer, String> cache;
        private final CountDownLatch tasksCounter;

        private NumberProducer(int id, String name, Cache<Integer, String> cache, CountDownLatch tasksCounter) {
            this.id = id;
            this.name = name;
            this.cache = cache;
            this.tasksCounter = tasksCounter;
        }

        @Override
        public void run() {
            cache.put(id, name);
            tasksCounter.countDown();
        }
    }
}

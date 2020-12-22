package com.tj.cardsagainsthumanity.core.cache;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class BasicCacheTest {

    private BasicCache<String, String> cache;
    private BasicCache<String, String> expireCache;

    @Before
    public void setUp() {
        cache = new BasicCache<>();
        expireCache = new BasicCache<>(1);
    }

    @Test
    public void putItem() {
        //it puts items in cache
        cache.putItem("key", "val1");
        assertEquals(cache.size(), 1);
        //it overrides existing items
        cache.putItem("key", "val2");
        assertEquals(cache.size(), 1);
    }

    @Test
    public void removeItem() {
        cache.putItem("key", "val1");
        assertEquals(cache.size(), 1);
        cache.removeItem("key");
        assertEquals(cache.size(), 0);
    }

    @Test
    public void getItem() {
        cache.putItem("key", "val1");
        cache.putItem("key", "val2");
        assertEquals(cache.getItem("key").get(), "val2");
        assertEquals(cache.getItem("key3"), Optional.empty());
    }

    @Test
    public void clear() {
        cache.putItem("key", "val1");
        cache.putItem("key2", "val2");
        assertEquals(cache.size(), 2);
        cache.clear();
        assertEquals(cache.size(), 0);
    }

    @Test
    public void clearExpiredItems() throws InterruptedException {
        expireCache.putItem("key1", "val");
        Thread.sleep(1001);
        expireCache.putItem("key2", "val2");
        expireCache.clearExpiredItems();
        assertEquals(expireCache.size(), 1);

    }

    @Test
    public void touchItem() throws InterruptedException {
        expireCache.putItem("key1", "val");
        Thread.sleep(1001);
        expireCache.touchItem("key1");
        expireCache.putItem("key2", "val2");
        expireCache.clearExpiredItems();
        assertEquals(expireCache.size(), 2);

    }

    @Test
    public void itemExists() throws InterruptedException {
        expireCache.putItem("key1", "val");
        assertTrue(expireCache.itemExists("key1"));
        expireCache.clear();
        assertFalse(expireCache.itemExists("key1"));
    }

    @Test
    public void getCacheItems() {
        cache.putItem("key", "val1");
        cache.putItem("key2", "val2");
        Collection<String> cacheItems = cache.getCacheItems();
        assertEquals(cacheItems.size(), 2);
        assertTrue(cacheItems.contains("val1"));
        assertTrue(cacheItems.contains("val2"));
    }

    @Test
    public void isItemExpired() throws InterruptedException {
        expireCache.putItem("key1", "val");
        assertFalse(expireCache.isItemExpired("key1"));
        Thread.sleep(1001);
        assertTrue(expireCache.isItemExpired("key1"));
    }
}
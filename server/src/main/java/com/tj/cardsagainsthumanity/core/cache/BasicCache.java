package com.tj.cardsagainsthumanity.core.cache;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BasicCache<K, I> implements Cache<K, I> {

    private Map<K, CacheItem<I>> cache;
    private Integer expirationTimeInSeconds;


    public BasicCache(Integer expirationSeconds) {
        this.cache = new HashMap<>();
        this.expirationTimeInSeconds = expirationSeconds;
    }

    public BasicCache() {
        this(null);
    }

    @Override
    public void putItem(K key, I item) {
        cache.put(key, new CacheItem<>(item, expirationTimeInSeconds));
    }

    @Override
    public void removeItem(K key) {
        cache.remove(key);
    }

    @Override
    public Optional<I> getItem(K key) {
        Optional<CacheItem<I>> cacheItem = get(key);
        if (cacheItem.isPresent()) {
            I item = cacheItem.get().getItem();
            return Optional.of(item);
        }
        return Optional.empty();
    }

    private Optional<CacheItem<I>> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void clearExpiredItems() {
        Predicate<Map.Entry<K, CacheItem<I>>> entryIsExpired = entry -> entry.getValue().isExpired();
        Consumer<Map.Entry<K, CacheItem<I>>> removeEntry = entry -> cache.remove(entry.getKey());
        List<Map.Entry<K, CacheItem<I>>> toRemove = cache.entrySet().stream()
                .filter(entryIsExpired)
                .collect(Collectors.toList());

        toRemove.forEach(removeEntry);
    }

    @Override
    public void touchItem(K key) {
        get(key).ifPresent(
                item -> item.updateExpiration(expirationTimeInSeconds)
        );
    }

    @Override
    public Collection<I> getCacheItems() {
        return cache.values()
                .stream()
                .map(CacheItem::getItem)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isItemExpired(K itemKey) {
        return get(itemKey)
                .map(CacheItem::isExpired)
                .orElse(false);
    }

    public int size() {
        return cache.size();
    }
}

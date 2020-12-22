package com.tj.cardsagainsthumanity.core.cache;

import java.util.Collection;
import java.util.Optional;

public interface Cache<K, T> {

    void putItem(K key, T item);

    void removeItem(K key);

    Optional<T> getItem(K key);

    void clear();

    void clearExpiredItems();

    void touchItem(K key);

    Collection<T> getCacheItems();

    boolean isItemExpired(K itemKey);

    default boolean itemExists(K itemKey) {
        return getItem(itemKey).isPresent();
    }


}

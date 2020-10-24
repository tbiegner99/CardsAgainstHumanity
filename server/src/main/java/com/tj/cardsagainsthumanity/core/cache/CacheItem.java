package com.tj.cardsagainsthumanity.core.cache;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

public class CacheItem<I> {
    private I item;
    private GregorianCalendar expiration;

    public CacheItem(I item, Integer expirationInSeconds) {
        this.item = item;
        updateExpiration(expirationInSeconds);
    }

    public I getItem() {
        return item;
    }

    public GregorianCalendar getExpiration() {
        return expiration;
    }

    public void updateExpiration(Integer expirationInSeconds) {
        updateExpiration(Optional.ofNullable(expirationInSeconds));
    }

    private void updateExpiration(Optional<Integer> expireTimeInSeconds) {
        expireTimeInSeconds.ifPresent(expireTime -> {
            this.expiration = now();
            this.expiration.add(Calendar.SECOND, expireTime);
        });

    }

    private GregorianCalendar now() {
        return new GregorianCalendar();
    }

    public boolean isExpired() {
        return getExpiration().compareTo(now()) <= 0;
    }

}

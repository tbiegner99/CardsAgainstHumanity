package com.tj.cardsagainsthumanity.security.http.resolvers;

public interface Resolver<I, O> {

    public O resolveFrom(I input);
}

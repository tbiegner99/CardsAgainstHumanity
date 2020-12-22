package com.tj.cardsagainsthumanity.serializer;

public interface RequestConverter<I, O> {
    O convertRequestToBusinessObject(I objectToConvert);
}

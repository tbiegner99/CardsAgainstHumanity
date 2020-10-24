package com.tj.cardsagainsthumanity.serializer;

public interface ResponseConverter<I, O> {
    O convertBusinessObjectToResponse(I businessObject);
}

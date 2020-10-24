package com.tj.cardsagainsthumanity.serializer;

public interface Serializer<Request, BusinessObject, Response> extends RequestConverter<Request, BusinessObject>, ResponseConverter<BusinessObject, Response> {
}

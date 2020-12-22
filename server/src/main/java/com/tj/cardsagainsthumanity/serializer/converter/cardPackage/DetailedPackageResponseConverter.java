package com.tj.cardsagainsthumanity.serializer.converter.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.DetailedCardPackage;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.DetailedPackageResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailedPackageResponseConverter implements ResponseConverter<DetailedCardPackage, DetailedPackageResponse> {
    private ResponseConverter<CardPackage, PackageResponse> packageResponseConverter;

    @Autowired
    public DetailedPackageResponseConverter(ResponseConverter<CardPackage, PackageResponse> packageResponseConverter) {
        this.packageResponseConverter = packageResponseConverter;
    }

    @Override
    public DetailedPackageResponse convertBusinessObjectToResponse(DetailedCardPackage businessObject) {
        if (businessObject == null) {
            return null;
        }
        List<CardResponse> whiteCards = businessObject.getWhiteCards().stream().map(this::convertCard).collect(Collectors.toList());
        List<CardResponse> blackCards = businessObject.getBlackCards().stream().map(this::convertCard).collect(Collectors.toList());
        return new DetailedPackageResponse(packageResponseConverter.convertBusinessObjectToResponse(businessObject.getPackageInfo()), whiteCards, blackCards);
    }

    private CardResponse convertCard(Card card) {
        return new CardResponse(card.getId(), card.getText(), null);
    }
}

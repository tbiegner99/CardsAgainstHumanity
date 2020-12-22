package com.tj.cardsagainsthumanity.serializer.converter.packageImport;

import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateBlackCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateWhiteCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PackageImportSerializer implements RequestConverter<NormalizedPackageImport, PackageImport> {
    private CreatePackageSerializer packageSerializer;
    private CreateWhiteCardRequestConverter whiteCardSerializer;
    private CreateBlackCardRequestConverter blackCardSerializer;

    public PackageImportSerializer(@Autowired CreatePackageSerializer packageSerializer, @Autowired CreateWhiteCardRequestConverter whiteCardSerializer, @Autowired CreateBlackCardRequestConverter blackCardSerializer) {
        this.packageSerializer = packageSerializer;
        this.whiteCardSerializer = whiteCardSerializer;
        this.blackCardSerializer = blackCardSerializer;
    }

    @Override
    public PackageImport convertRequestToBusinessObject(NormalizedPackageImport objectToConvert) {
        Set<Card> cardsToImport = getCardsToImport(objectToConvert);
        CardPackage packageToImport = getPackageToImport(objectToConvert);
        return new PackageImport(packageToImport, cardsToImport);
    }

    private CardPackage getPackageToImport(NormalizedPackageImport objectToConvert) {

        return packageSerializer.convertRequestToBusinessObject(objectToConvert.getPackageInfo());
    }

    private Set<Card> getCardsToImport(NormalizedPackageImport objectToConvert) {
        Set<Card> whiteCard = importWhiteCards(objectToConvert.getWhiteCards());
        Set<Card> blackCard = importBlackCards(objectToConvert.getBlackCards());
        Set<Card> result = new HashSet<>(whiteCard);
        result.addAll(blackCard);
        return result;
    }

    private Set<Card> importWhiteCards(Collection<CreateCardRequest> whiteCards) {
        return whiteCards.stream()
                .map(whiteCardSerializer::convertRequestToBusinessObject)
                .collect(Collectors.toSet());
    }

    private Set<Card> importBlackCards(Collection<CreateCardRequest> blackCards) {
        return blackCards.stream()
                .map(blackCardSerializer::convertRequestToBusinessObject)
                .collect(Collectors.toSet());
    }
}

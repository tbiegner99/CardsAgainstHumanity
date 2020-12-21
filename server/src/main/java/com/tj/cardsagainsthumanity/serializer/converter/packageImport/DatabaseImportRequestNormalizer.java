package com.tj.cardsagainsthumanity.serializer.converter.packageImport;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseCardImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabasePackageImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseImportRequestNormalizer {
    public Collection<NormalizedPackageImport> normalize(DatabaseImportRequest request) {
        List<DatabaseCardImportRequest> whiteCards = request.getWhiteCards();
        List<DatabaseCardImportRequest> blackCards = request.getBlackCards();
        Collection<DatabasePackageImportRequest> packages = request.getUniqueDecks().values();


        return packages.stream()
                .map(pack -> normalizePackage(pack, whiteCards, blackCards))
                .collect(Collectors.toList());
    }

    private NormalizedPackageImport normalizePackage(DatabasePackageImportRequest pack, List<DatabaseCardImportRequest> whiteCards, List<DatabaseCardImportRequest> blackCards) {
        NormalizedPackageImport result = new NormalizedPackageImport();
        result.setPackageInfo(createPackageInfo(pack));

        result.setWhiteCards(convertCards(pack.getWhite()));
        result.setBlackCards(convertCards(pack.getBlack()));
        return result;
    }

    private CreatePackageRequest createPackageInfo(DatabasePackageImportRequest pack) {
        return new CreatePackageRequest(pack.getName(), pack.getIcon(), pack.isOfficial(), CardPackage.IconType.FONTAWESOME);
    }


    private List<CreateCardRequest> convertCards(List<DatabaseCardImportRequest> cards) {
        return cards.stream()
                .map(card -> new CreateCardRequest(card.getText(), card.getPick(), card.getPackageId()))
                .collect(Collectors.toList());
    }


}

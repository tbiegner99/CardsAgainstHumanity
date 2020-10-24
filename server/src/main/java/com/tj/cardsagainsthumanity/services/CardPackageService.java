package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.CardPackageDao;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardPackageService {
    private CardPackageDao cardPackageDao;
    private CardDao cardDao;

    @Autowired
    public CardPackageService(CardPackageDao cardPackageDao, CardDao cardDao) {
        this.cardPackageDao = cardPackageDao;
        this.cardDao = cardDao;
    }

    public CardPackage deleteCardPackage(CardPackage cardPackage) {

        return cardPackageDao.deleteCardPackage(cardPackage);
    }

    public CardPackage createCardPackage(CardPackage card) {
        return cardPackageDao.saveCardPackage(card);
    }

    public CardPackage getCardPackageById(Integer packageId) {
        return cardPackageDao.getCardPackageById(packageId);
    }

    public Collection<CardPackage> importPackages(Collection<PackageImport> packagesToImport) {
        return packagesToImport.stream()
                .map(this::importPackage)
                .collect(Collectors.toList());
    }

    private CardPackage importPackage(PackageImport packageImport) {
        CardPackage pack = cardPackageDao.saveOrGetExisting(packageImport.getCardPackage());
        packageImport.getCardsToImport().forEach(card -> createCardInPackage(card, pack));

        return pack;

    }


    private void createCardInPackage(Card card, CardPackage pack) {
        card.setPackage(pack);
        cardDao.saveCard(card);
    }
}

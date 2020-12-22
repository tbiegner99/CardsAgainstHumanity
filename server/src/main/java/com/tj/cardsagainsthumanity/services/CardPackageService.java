package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.CardPackageDao;
import com.tj.cardsagainsthumanity.dao.DeckDao;
import com.tj.cardsagainsthumanity.models.cards.*;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardPackageService {
    private CardPackageDao cardPackageDao;
    private CardDao cardDao;
    private DeckDao deckDao;

    @Autowired
    public CardPackageService(DeckDao deckDao, CardPackageDao cardPackageDao, CardDao cardDao) {
        this.deckDao = deckDao;
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

    public List<CardPackage> getPackagesForPlayer(Player player) {
        return cardPackageDao.getPackagesForPlayer(player.getId());
    }

    public List<CardPackage> getUsablePackagesForPlayer(Player player) {
        return cardPackageDao.getUsablePackagesForPlayer(player.getId());
    }


    public Collection<CardPackage> importPackages(Collection<PackageImport> packagesToImport) {
        return packagesToImport.stream()
                .map(this::importPackage)
                .collect(Collectors.toList());
    }

    private CardPackage importPackage(PackageImport packageImport) {
        CardPackage pack = cardPackageDao.saveOrGetExisting(packageImport.getCardPackage());
        packageImport.getUniqueCards().forEach(card -> createCardInPackage(card, pack));

        return pack;

    }


    public Card createCardInPackage(Card card, CardPackage pack) {
        card.setCardPackage(pack);
        return cardDao.saveCard(card);

    }

    public void updateCardCountForPackage(Integer cardPackageId) {
        cardPackageDao.updateCardCountForPackage(cardPackageId);
        deckDao.updateCardCountForDecksThatUsePackage(cardPackageId);
    }


    public DetailedCardPackage getPackageCardsForPackage(Integer packageId) {
        CardPackage packageInfo = this.getCardPackageById(packageId);
        List<WhiteCard> whiteCards = cardDao.getWhiteCardsForPackage(packageId);
        List<BlackCard> blackCards = cardDao.getBlackCardsForPackage(packageId);
        return new DetailedCardPackage(packageInfo, whiteCards, blackCards);
    }

    public void assertCanReadPackage(Player owner, Integer packageId) {
    }

    public void assertCanEditPackage(Player owner, Integer packageId) {
    }
}

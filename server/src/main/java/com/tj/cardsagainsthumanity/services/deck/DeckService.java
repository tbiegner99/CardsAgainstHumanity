package com.tj.cardsagainsthumanity.services.deck;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.DeckDao;
import com.tj.cardsagainsthumanity.models.cards.*;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.converter.deck.DeckResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional()
public class DeckService {
    private DeckDao deckDao;
    private CardDao cardDao;
    private DeckResponseConverter responseConverter;

    @Autowired
    public DeckService(DeckDao deckDao, CardDao cardDao, DeckResponseConverter responseConverter) {
        this.responseConverter = responseConverter;
        this.cardDao = cardDao;
        this.deckDao = deckDao;
    }


    public DeckInfo createDeck(DeckInfo deck) {
        return deckDao.createDeck(deck);
    }

    public DeckCards getDeckInfo(Integer deckId) {
        return deckDao.getDeckCards(deckId);
    }


    public List<DeckInfo> getPlayableDecksForPlayer(Player player) {
        return deckDao.getPlayableDecksForPlayer(player.getId());
    }

    public List<DeckInfo> getEditableDecksForPlayer(Player player) {
        return deckDao.getEditableDecksForPlayer(player.getId());
    }

    public DeckInfo addPackageToDeck(Integer deckId, Integer packageId) {
        deckDao.addPackage(deckId, packageId);
        return deckDao.loadDeckInfo(deckId);
    }

    public DeckInfo removePackageFromDeck(Integer deckId, Integer packageId) {
        deckDao.deletePackage(deckId, packageId);
        return deckDao.loadDeckInfo(deckId);
    }

    public DeckInfo addCardToDeck(Integer deckId, CardType type, Integer packageId, Integer cardId) {
        deckDao.addCard(deckId, packageId, cardId, type);
        return deckDao.loadDeckInfo(deckId);
    }

    public DeckInfo removeCardFromDeck(Integer deckId, CardType type, Integer cardId) {
        Card card;
        if (type == CardType.WHITE) {
            card = cardDao.getWhiteCard(cardId);
        } else {
            card = cardDao.getBlackCard(cardId);
        }
        if (card != null) {
            deckDao.removeCard(deckId, card.getCardPackage().getId(), cardId, type);
        }
        return deckDao.loadDeckInfo(deckId);
    }

    private boolean isAllPackagesEntry(DeckEntry entry) {
        return entry.getPackageId() == null && entry.getCardId() == null;
    }


    private DeckPackageInfo mergeDeckPackageEntry(DeckEntry entry, DeckPackageInfo existing) {
        return new DeckPackageInfo(entry).merge(existing);
    }


    public DeckDetails loadDeckDetails(Integer deckId) {
        DeckInfo deckInfo = deckDao.loadDeckInfo(deckId);
        List<DeckEntry> deckEntries = deckDao.loadEntriesForDeck(deckId);
        Map<Integer, DeckPackageInfo> deckPackages = new HashMap<>();
        boolean includeAllPackages = false;
        for (DeckEntry entry : deckEntries) {
            if (isAllPackagesEntry(entry)) {
                includeAllPackages = true;
            } else {
                Integer packageId = entry.getPackageId();
                deckPackages.put(packageId, mergeDeckPackageEntry(entry, deckPackages.get(packageId)));
            }
        }
        DeckResponse basicInfo = responseConverter.convertBusinessObjectToResponse(deckInfo);
        return new DeckDetails(basicInfo, includeAllPackages, deckPackages);
    }


    public void assertUserCanEdit(Player player, Integer deckId) {
        if (player == null || !deckDao.userOwns(player.getId(), deckId)) {
            throw new IllegalStateException("User can not edit this deck");
        }
    }

    public void assertUserCanRead(Player player, Integer deckId) {
        if (player == null || !deckDao.userHasReadAccess(player.getId(), deckId)) {
            throw new IllegalStateException("User can not view this deck");
        }
    }

    public void deleteDeck(Integer deckId) {
        deckDao.deleteDeck(deckId);
    }
}

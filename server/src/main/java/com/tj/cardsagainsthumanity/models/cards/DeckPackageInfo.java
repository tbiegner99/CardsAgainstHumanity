package com.tj.cardsagainsthumanity.models.cards;

import java.util.HashMap;
import java.util.Map;

public class DeckPackageInfo {
    private boolean allCards = false;
    private Map<Integer, Boolean> whiteCardInclusions;
    private Map<Integer, Boolean> blackCardInclusions;

    public DeckPackageInfo() {
        whiteCardInclusions = new HashMap<>();
        blackCardInclusions = new HashMap<>();
    }

    public DeckPackageInfo(DeckEntry entry) {
        this();
        if (entry.getCardId() == null) {
            this.allCards = true;
        } else if (entry.isExclude()) {
            this.excludeCard(entry.getCardId(), entry.getCardTypeEnum());
        } else {
            this.includeCard(entry.getCardId(), entry.getCardTypeEnum());
        }
    }

    public DeckPackageInfo merge(DeckPackageInfo info) {
        if (info != null) {
            allCards = allCards || info.allCards;
            whiteCardInclusions.putAll(info.whiteCardInclusions);
            blackCardInclusions.putAll(info.blackCardInclusions);
        }
        return this;
    }

    private Map<Integer, Boolean> getCardsForType(CardType cardType) {
        if (cardType == CardType.WHITE) {
            return whiteCardInclusions;
        }
        return blackCardInclusions;
    }

    public void includeCard(Integer cardId, CardType cardType) {
        getCardsForType(cardType).put(cardId, true);
    }

    public void excludeCard(Integer cardId, CardType cardType) {
        getCardsForType(cardType).put(cardId, false);
    }

    public boolean isAllCards() {
        return allCards;
    }

    public void setAllCards(boolean allCards) {
        this.allCards = allCards;
    }

    public Map<Integer, Boolean> getWhiteCardInclusions() {
        return whiteCardInclusions;
    }

    public void setWhiteCardInclusions(Map<Integer, Boolean> whiteCardInclusions) {
        this.whiteCardInclusions = whiteCardInclusions;
    }

    public Map<Integer, Boolean> getBlackCardInclusions() {
        return blackCardInclusions;
    }

    public void setBlackCardInclusions(Map<Integer, Boolean> blackCardInclusions) {
        this.blackCardInclusions = blackCardInclusions;
    }
}

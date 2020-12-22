package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;

import java.util.Map;

public class DeckDetails {

    private DeckResponse deckInfo;
    private boolean includeAll;
    private Map<Integer, DeckPackageInfo> packages;

    public DeckDetails() {
    }

    public DeckDetails(DeckResponse deckInfo, boolean includeAll, Map<Integer, DeckPackageInfo> packages) {
        this.deckInfo = deckInfo;
        this.includeAll = includeAll;
        this.packages = packages;
    }

    public DeckResponse getDeckInfo() {
        return deckInfo;
    }

    public void setDeckInfo(DeckResponse deckInfo) {
        this.deckInfo = deckInfo;
    }

    public boolean isIncludeAll() {
        return includeAll;
    }

    public void setIncludeAll(boolean includeAll) {
        this.includeAll = includeAll;
    }

    public Map<Integer, DeckPackageInfo> getPackages() {
        return packages;
    }

    public void setPackages(Map<Integer, DeckPackageInfo> packages) {
        this.packages = packages;
    }
}

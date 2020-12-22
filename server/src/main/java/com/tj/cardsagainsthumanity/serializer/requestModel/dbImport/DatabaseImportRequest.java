package com.tj.cardsagainsthumanity.serializer.requestModel.dbImport;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseImportRequest {
    private Map<String, DatabasePackageImportRequest> decks;

    private List<DatabaseCardImportRequest> whiteCards;

    private List<DatabaseCardImportRequest> blackCards;

    private List<String> order;

    public DatabaseImportRequest() {
        decks = new HashMap<>();
    }

    public Map<String, DatabasePackageImportRequest> getDecks() {
        return decks;
    }

    public Map<String, DatabasePackageImportRequest> getUniqueDecks() {
        Map<String, DatabasePackageImportRequest> ret = new HashMap<>();
        for (DatabasePackageImportRequest p : getDecks().values()) {
            String name = p.getName().toLowerCase();
            if (ret.containsKey(p.getName())) {
                ret.put(name, ret.get(name).merge(p));
            } else {
                ret.put(name, p);
            }

        }
        return ret;

    }


    @JsonAnySetter
    private void setDecks(String name, DatabasePackageImportRequest deck) {
        decks.put(name, deck);
    }

    public List<DatabaseCardImportRequest> getWhiteCards() {
        return whiteCards;
    }

    public void setWhiteCards(List<String> whiteCards) {
        this.whiteCards = whiteCards.stream()
                .map(text -> new DatabaseCardImportRequest(text))
                .collect(Collectors.toList());
    }

    public List<DatabaseCardImportRequest> getBlackCards() {
        return blackCards;
    }

    public void setBlackCards(List<DatabaseCardImportRequest> blackCards) {
        this.blackCards = blackCards;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }
}

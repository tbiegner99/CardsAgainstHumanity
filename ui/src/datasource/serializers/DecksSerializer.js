class DeckSerializer {
  serializeCreatePackageData(data) {
    return {
      name: data.deckName
    };
  }

  deserializePlayer(player) {
    if (player == null) {
      return null;
    }
    return {
      id: player.id,
      displayName: player.displayName
    };
  }

  deserializeDeckDetailsResponse(deck) {
    const deckInfo = this.deserializeDeckResponse(deck.deckInfo);

    return {
      ...deckInfo,
      packages: deck.packages
    };
  }

  deserializeDeckResponse(deck) {
    return {
      deckId: deck.id,
      name: deck.name,
      owner: this.deserializePlayer(deck.owner),
      whiteCardCount: deck.whiteCardCount,
      blackCardCount: deck.blackCardCount
    };
  }

  deserializeMyDeckResponse(decks) {
    return decks.map((deck) => this.deserializeDeckResponse(deck));
  }
}

export default new DeckSerializer();

package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {
    private CardDao cardDao;

    @Autowired
    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public Card deleteCard(Card card) {

        return cardDao.deleteCard(card);
    }

    public Card createCard(Card card) {
        return cardDao.saveCard(card);
    }

    public Collection<Card> createCards(Collection<Card> cardsToAdd) {
        return cardsToAdd.stream()
                .map(this::createCard)
                .collect(Collectors.toList());
    }
}

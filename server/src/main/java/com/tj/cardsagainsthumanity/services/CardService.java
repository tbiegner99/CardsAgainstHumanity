package com.tj.cardsagainsthumanity.services;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.bulk.BulkReport;
import com.tj.cardsagainsthumanity.models.bulk.CardError;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {
    private CardDao cardDao;
    private CardPackageService cardPackageService;

    @Autowired
    public CardService(CardPackageService cardPackageService, CardDao cardDao) {
        this.cardDao = cardDao;
        this.cardPackageService = cardPackageService;
    }

    public Card deleteWhiteCard(Integer packageId, Integer cardId) {
        Card card = cardDao.getWhiteCard(packageId, cardId);
        return deleteCard(card);
    }

    public Card deleteBlackCard(Integer packageId, Integer cardId) {
        Card card = cardDao.getBlackCard(packageId, cardId);
        return deleteCard(card);
    }

    public Card updateWhiteCard(Integer packageId, Integer cardId, String text) {
        Card card = cardDao.getWhiteCard(packageId, cardId);
        return updateCard(card, text);
    }

    public Card updateCard(Card card, String text) {
        card.setText(text);
        return cardDao.saveCard(card);
    }

    public Card updateBlackCard(Integer packageId, Integer cardId, String text) {
        Card card = cardDao.getBlackCard(packageId, cardId);
        return updateCard(card, text);
    }


    public Card deleteCard(Card card) {

        Card ret = cardDao.deleteCard(card);
        cardPackageService.updateCardCountForPackage(ret.getCardPackage().getId());
        return ret;
    }

    public Card createCard(Integer packageId, Card card) {
        CardPackage pack = cardPackageService.getCardPackageById(packageId);
        return createCard(pack, card);

    }

    public Card createCard(CardPackage pack, Card card) {
        card.setCardPackage(pack);
        Card ret = cardPackageService.createCardInPackage(card, pack);
        cardPackageService.updateCardCountForPackage(ret.getCardPackage().getId());
        return ret;

    }

    public Collection<Card> createCards(Integer packageId, Collection<Card> cardsToAdd) {
        CardPackage pack = cardPackageService.getCardPackageById(packageId);
        return cardsToAdd.stream()
                .map((card) -> createCard(pack, card))
                .collect(Collectors.toList());
    }

    public Card refresh(Card savedCard) {
        cardDao.refresh(savedCard);
        return savedCard;
    }

    public BulkReport bulkCreateCards(Integer packageId, List<Card> cards) {
        int successes = 0;
        int failures = 0;
        int inserts = cards.size();
        List<CardError> errors = new ArrayList<>();
        CardPackage pack = cardPackageService.getCardPackageById(packageId);
        for (Card c : cards) {
            try {
                this.createCard(pack, c);
                successes++;
            } catch (Exception e) {
                Optional<CardError> error = handleBulkError(c, e);
                if (error.isPresent()) {
                    errors.add(error.get());
                    failures++;
                } else {
                    successes++;
                }
            }
        }
        return new BulkReport(inserts, successes, failures, errors);
    }

    private Optional<CardError> handleBulkError(Card card, Exception error) {
        try {
            throw error;
        } catch (MySQLIntegrityConstraintViolationException e) {
            return Optional.empty(); //treat conflicts as
        } catch (Exception e) {
            return Optional.of(new CardError(card.getText(), e.getMessage(), 500));
        }
    }
}


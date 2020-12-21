package helpers.selectors;

import static helpers.selectors.SelectorsHelper.*;

public interface GamePlay {
    String CZAR_PAGE = metaId("czar-page");
    String GAMEPLAY_PAGE = metaId("gameplay-page");
    String  SELECTABLE_CARD= parentContains(metaId("card"),metaId("selectable"));
    String MAKE_PLAY_BUTTON = cssClass("make-play-button");
    String CANCEL_PLAY_BUTTON = cssClass("cancel-play-button");
    String REVEAL_PLAY_BUTTON = metaId("reveal-play-button");
    String CARDS_TO_PLAY = metaId("cards-to-play");
    String CONFIRM_WINNER = metaId("confirm-winner");
    String CARD_PLAY=metaId("card-play-submission");
    String NEXT_ROUND_BUTTON=metaId("next-round-button");
    String PLAYS_TO_REVEAL = metaId("plays-to-reveal");
}

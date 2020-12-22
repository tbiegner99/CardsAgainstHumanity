package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.springframework.stereotype.Component;

@Component
public class PlayerResponseConverter implements ResponseConverter<Player, PlayerResponse> {
    @Override
    public PlayerResponse convertBusinessObjectToResponse(Player businessObject) {
        if (businessObject == null) {
            return null;
        }
        return PlayerResponse.builder()
                .id(businessObject.getId())
                .displayName(businessObject.getDisplayName())
                .email(businessObject.getEmail())
                .firstName(businessObject.getFirstName())
                .lastName(businessObject.getLastName())
                .build();
    }
}

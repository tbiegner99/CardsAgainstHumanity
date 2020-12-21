package com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Objects;

public class CreatePackageRequest {
    @NotNull
    private String packageName;
    @Null
    @JsonIgnore
    private Player owner;
    private boolean official;
    private String icon;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CardPackage.IconType iconType;


    public CreatePackageRequest(String packageName, String icon, boolean official, CardPackage.IconType iconType) {
        this.packageName = packageName;
        this.icon = icon;
        this.iconType = iconType;
        this.official = official;
    }

    public CreatePackageRequest() {
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public CardPackage.IconType getIconType() {
        return iconType;
    }

    public void setIconType(CardPackage.IconType iconType) {
        this.iconType = iconType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        CreatePackageRequest that = (CreatePackageRequest) o;
        return Objects.equals(getPackageName(), that.getPackageName()) &&
                Objects.equals(getIcon(), that.getIcon()) &&
                getIconType() == that.getIconType();
    }
}


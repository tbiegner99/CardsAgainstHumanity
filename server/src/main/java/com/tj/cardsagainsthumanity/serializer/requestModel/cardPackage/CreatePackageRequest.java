package com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;

import java.util.Objects;

public class CreatePackageRequest {
    private String packageName;
    private String icon;
    private CardPackage.IconType iconType;


    public CreatePackageRequest(String packageName, String icon, CardPackage.IconType iconType) {
        this.packageName = packageName;
        this.icon = icon;
        this.iconType = iconType;
    }

    public CreatePackageRequest() {
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


    @Override
    public boolean equals(Object o) {
        CreatePackageRequest that = (CreatePackageRequest) o;
        return Objects.equals(getPackageName(), that.getPackageName()) &&
                Objects.equals(getIcon(), that.getIcon()) &&
                getIconType() == that.getIconType();
    }
}


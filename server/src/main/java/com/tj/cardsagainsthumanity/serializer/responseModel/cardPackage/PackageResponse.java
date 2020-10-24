package com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;

import java.util.Objects;

public class PackageResponse {
    private String packageName;
    private CardPackage.IconType iconType;
    private String icon;

    public PackageResponse(String packageName, CardPackage.IconType iconType, String icon) {
        this.packageName = packageName;
        this.iconType = iconType;
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public CardPackage.IconType getIconType() {
        return iconType;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        PackageResponse that = (PackageResponse) o;
        return Objects.equals(getPackageName(), that.getPackageName()) &&
                Objects.equals(getIconType(), that.getIconType()) &&
                Objects.equals(getIcon(), that.getIcon());
    }
}

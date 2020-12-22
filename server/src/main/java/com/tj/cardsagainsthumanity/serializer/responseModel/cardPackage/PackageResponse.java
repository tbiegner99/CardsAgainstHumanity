package com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;

import java.util.Objects;

public class PackageResponse {
    private Integer id;
    private String packageName;
    private CardPackage.IconType iconType;
    private String icon;
    private Integer ownerId;
    private Integer whiteCardCount;
    private Integer blackCardCount;

    public PackageResponse(Integer id, String packageName, Integer ownerId, CardPackage.IconType iconType, String icon, Integer whiteCardCount, Integer blackCardCount) {
        this.id = id;
        this.packageName = packageName;
        this.iconType = iconType;
        this.icon = icon;
        this.ownerId = ownerId;
        this.whiteCardCount = whiteCardCount;
        this.blackCardCount = blackCardCount;
    }

    public Integer getId() {
        return id;
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

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getWhiteCardCount() {
        return whiteCardCount;
    }

    public Integer getBlackCardCount() {
        return blackCardCount;
    }

    @Override
    public boolean equals(Object o) {
        PackageResponse that = (PackageResponse) o;
        return Objects.equals(getPackageName(), that.getPackageName()) &&
                Objects.equals(getIconType(), that.getIconType()) &&
                Objects.equals(getIcon(), that.getIcon());
    }
}

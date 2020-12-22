package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.AuditedEntity;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "card_package")
public class CardPackage extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "package_id")
    private Integer id;
    private String name;
    private String icon;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "icon_type")
    private IconType iconType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    @Fetch(FetchMode.JOIN)
    private Player owner;
    private boolean official;
    private Integer whiteCardCount = 0;
    private Integer blackCardCount = 0;

    public CardPackage() {
    }

    public CardPackage(String name) {
        this();
        setName(name);
    }


    public CardPackage(String name, String icon, IconType iconType) {
        this(name);
        setIcon(icon);
        setIconType(iconType);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {
        this.iconType = iconType;
    }

    @Override
    public boolean equals(Object o) {
        CardPackage that = (CardPackage) o;
        return Objects.equals(getName(), that.getName());
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer getWhiteCardCount() {
        return whiteCardCount;
    }

    public void setWhiteCardCount(Integer whiteCardCount) {
        this.whiteCardCount = whiteCardCount;
    }

    public Integer getBlackCardCount() {
        return blackCardCount;
    }

    public void setBlackCardCount(Integer blackCardCount) {
        this.blackCardCount = blackCardCount;
    }

    public enum IconType {
        FONTAWESOME,
        IMAGE,
        SVG,
        URL
    }


}

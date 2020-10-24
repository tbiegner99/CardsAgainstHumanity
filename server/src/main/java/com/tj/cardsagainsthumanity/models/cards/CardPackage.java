package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.AuditedEntity;

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

    public enum IconType {
        FONTAWESOME,
        SVG,
        URL
    }


}

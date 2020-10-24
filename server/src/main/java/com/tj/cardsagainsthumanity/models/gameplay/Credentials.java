package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.AuditedEntity;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
public class Credentials extends AuditedEntity {
    @Id
    @Column(name = "user_id")
    private Integer playerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private Player player;

    private String password;


    public Credentials() {

    }

    public Credentials(String password) {
        setPassword(password);
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}

package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.AuditedEntity;
import com.tj.cardsagainsthumanity.models.gameplay.game.Voter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class Player extends AuditedEntity implements Voter, Serializable {
    public static Player ANONYMOUS = new Player();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Credentials credentials;
    @OneToMany(mappedBy = "czar")
    private Set<GameRound> judgements;
    @OneToMany(mappedBy = "player")
    private Set<CardPlay> plays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<GameRound> getJudgements() {
        return judgements;
    }

    public void setJudgements(Set<GameRound> judgements) {
        this.judgements = judgements;
    }

    public Set<CardPlay> getPlays() {
        return plays;
    }

    public void setPlays(Set<CardPlay> plays) {
        this.plays = plays;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {

        credentials.setPlayer(this);
        this.credentials = credentials;
    }

    @Override
    public int hashCode() {
        if (this == Player.ANONYMOUS) {
            return UUID.randomUUID().hashCode();
        }
        return this.getId();
    }

    @Override
    public boolean equals(Object o) {
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId()) &&
                Objects.equals(getEmail(), player.getEmail()) &&
                Objects.equals(getDisplayName(), player.getDisplayName()) &&
                Objects.equals(getFirstName(), player.getFirstName()) &&
                Objects.equals(getLastName(), player.getLastName());
    }

    public boolean isCzarFor(GameRound round) {
        return round != null && this.getId().equals(round.getCzarId());
    }

    @Override
    public String getVoterId() {
        return "" + getId();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}

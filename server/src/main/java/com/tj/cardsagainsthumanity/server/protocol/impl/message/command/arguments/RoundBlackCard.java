package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import java.util.Objects;

public class RoundBlackCard {

    private Integer id;
    private String text;
    private Integer numberOfAnswers;

    public RoundBlackCard() {
    }

    public RoundBlackCard(Integer id, String text, Integer numberOfAnswers) {
        this.setId(id);
        this.setText(text);
        this.setNumberOfAnswers(numberOfAnswers);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(Integer numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    @Override
    public boolean equals(Object o) {
        RoundBlackCard that = (RoundBlackCard) o;
        return Objects.equals(getId(), that.id) &&
                Objects.equals(getText(), that.text) &&
                Objects.equals(getNumberOfAnswers(), that.numberOfAnswers);
    }

    @Override
    public String toString() {
        return "RoundBlackCard{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", numberOfAnswers=" + numberOfAnswers +
                '}';
    }
}

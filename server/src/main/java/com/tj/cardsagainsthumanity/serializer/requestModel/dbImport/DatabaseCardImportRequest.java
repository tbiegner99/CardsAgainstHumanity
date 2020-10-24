package com.tj.cardsagainsthumanity.serializer.requestModel.dbImport;

public class DatabaseCardImportRequest {
    private String text;

    public DatabaseCardImportRequest(String text) {
        setText(text);
    }

    public DatabaseCardImportRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

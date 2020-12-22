package com.tj.cardsagainsthumanity.serializer.requestModel.dbImport;

public class DatabaseCardImportRequest {
    private String text;
    private Integer packageId;
    private Integer pick;


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

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getPick() {
        return pick;
    }

    public void setPick(Integer pick) {
        this.pick = pick;
    }
}

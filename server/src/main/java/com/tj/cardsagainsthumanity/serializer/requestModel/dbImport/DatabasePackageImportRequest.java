package com.tj.cardsagainsthumanity.serializer.requestModel.dbImport;

import java.util.List;

public class DatabasePackageImportRequest {
    private String name;
    private List<DatabaseCardImportRequest> white;
    private List<DatabaseCardImportRequest> black;
    private String icon;
    private boolean official;

    public DatabasePackageImportRequest merge(DatabasePackageImportRequest other) {
        this.white.addAll(other.white);
        this.black.addAll(other.black);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatabaseCardImportRequest> getWhite() {
        return white;
    }

    public void setWhite(List<DatabaseCardImportRequest> white) {
        this.white = white;
    }

    public List<DatabaseCardImportRequest> getBlack() {
        return black;
    }

    public void setBlack(List<DatabaseCardImportRequest> black) {
        this.black = black;
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
}

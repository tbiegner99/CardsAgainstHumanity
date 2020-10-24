package com.tj.cardsagainsthumanity.serializer.requestModel.dbImport;

import java.util.List;

public class DatabasePackageImportRequest {
    private String name;
    private List<Integer> white;
    private List<Integer> black;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getWhite() {
        return white;
    }

    public void setWhite(List<Integer> white) {
        this.white = white;
    }

    public List<Integer> getBlack() {
        return black;
    }

    public void setBlack(List<Integer> black) {
        this.black = black;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

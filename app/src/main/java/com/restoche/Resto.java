package com.restoche;

public class Resto {
    private String title;
    private String localisation;
    private String image;

    public Resto(String title, String localisation) {
        this.title = title;
        this.localisation = localisation;
    }

    public String getTitle() {
        return title;
    }

    public String getLocalisation() {
        return localisation;
    }

}

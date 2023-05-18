package com.restoche;

public class Resto {
    private String title;
    private String localisation;
    private String image;

    public Resto() {
    }

    public Resto(String title, String localisation,String image) {
        this.title = title;
        this.localisation = localisation;
        this.image=image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocalisation() {
        return localisation;
    }

}

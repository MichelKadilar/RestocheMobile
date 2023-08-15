package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Resto implements Parcelable {
    private String title;
    private String localisation;
    private String image;

    private String spec;

    private String description;
    private float ratings;

    public Resto() {
    }

    public Resto(String title, String localisation, String image, float ratings) {
        this.title = title;
        this.localisation = localisation;
        this.image = image;
        this.ratings = ratings;
    }

    protected Resto(Parcel in) {
        title = in.readString();
        localisation = in.readString();
        image = in.readString();
        ratings = in.readFloat();
    }

    public static final Creator<Resto> CREATOR = new Creator<Resto>() {
        @Override
        public Resto createFromParcel(Parcel in) {
            return new Resto(in);
        }

        @Override
        public Resto[] newArray(int size) {
            return new Resto[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
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

    public String getSpec() {
        return "Italien, Francais";
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(localisation);
        dest.writeString(image);
        dest.writeFloat(ratings);
        dest.writeString(spec);
    }
}

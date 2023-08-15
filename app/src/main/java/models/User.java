package models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String password;
    private String birthday;
    private String id;
    private String nom;
    private String prenom;
    private String profil;
    private String pseudo;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.profil = profil;
        this.pseudo = pseudo;
    }

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        birthday = in.readString();
        id = in.readString();
        nom = in.readString();
        prenom = in.readString();
        profil = in.readString();
        pseudo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(birthday);
        dest.writeString(id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(profil);
        dest.writeString(pseudo);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}

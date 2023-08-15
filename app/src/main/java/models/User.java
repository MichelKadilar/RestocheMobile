package models;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class User implements Parcelable {
    public String email;
    public String password;
    public String birthday;
    public String uid;
    public String nom;
    public String prenom;
    public String profil;
    public String pseudo;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        birthday = in.readString();
        uid = in.readString();
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
        dest.writeString(uid);
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
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", uid='" + uid + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", profil='" + profil + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
    public void saveUserToPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", new Gson().toJson(this));
        editor.apply();
    }

    public static User getUserFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", "");
        return new Gson().fromJson(userJson, User.class);
    }



}

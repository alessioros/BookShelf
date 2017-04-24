package it.polimi.bookshelf.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    public String access_email;
    public String password;
    public String user_name;
    public String user_surname;

    public User() {}

    public User(String access_email, String password, String user_name, String user_surname) {
        this.access_email = access_email;
        this.password = password;
        this.user_name = user_name;
        this.user_surname = user_surname;
    }

    protected User(Parcel in) {
        access_email = in.readString();
        password = in.readString();
        user_name = in.readString();
        user_surname = in.readString();
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

    public String getAccess_email() {
        return access_email;
    }

    public void setAccess_email(String access_email) {
        this.access_email = access_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_email);
        dest.writeString(password);
        dest.writeString(user_name);
        dest.writeString(user_surname);
    }
}

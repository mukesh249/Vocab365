package com.example.advosoft.vocab365.wrapper;

/**
 * Created by advosoft on 10/7/2017.
 */

public class User {
    public String name;
    public String profilePicLink;

    public User(String name, String profilePicLink) {
        this.name = name;
        this.profilePicLink = profilePicLink;
    }

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }


}

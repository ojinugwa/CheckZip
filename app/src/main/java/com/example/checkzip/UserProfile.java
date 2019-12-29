package com.example.checkzip;

public class UserProfile {
    public String firstName;
    public String lastName;
    public String username;
    public String email;
    public static String zipcode = "19093";
    public String question;
    public String answer;


    public UserProfile(String firstName, String lastName, String username, String email, String zipcode, String question, String answer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.zipcode = zipcode;
        this.question = question;
        this.answer = answer;
    }
}

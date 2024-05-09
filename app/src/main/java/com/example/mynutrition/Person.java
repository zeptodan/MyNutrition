package com.example.mynutrition;

public abstract class Person {
    String username;
    String password;
    String name;
    String phonenum;

    public Person() {
    }

    public Person(String username, String password, String name, String phonenum) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenum = phonenum;
    }
}

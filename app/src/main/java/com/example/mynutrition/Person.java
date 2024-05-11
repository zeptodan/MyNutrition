package com.example.mynutrition;

public abstract class Person {
    String name;
    String phonenum;

    public Person() {
    }

    public Person(String name, String phonenum) {
        this.name = name;
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public String getPhonenum() {
        return phonenum;
    }
}

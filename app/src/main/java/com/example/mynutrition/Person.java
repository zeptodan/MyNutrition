package com.example.mynutrition;

public class Person {
    String name;
    String phonenum;
    String id;
    public Person() {
    }

    public Person(String name, String phonenum, String id) {
        this.name = name;
        this.phonenum = phonenum;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getId() {
        return id;
    }
}

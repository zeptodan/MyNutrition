package com.example.mynutrition;

public class Nutritionist extends Person{
    boolean available;
    public Nutritionist() {
        super();
    }

    public Nutritionist(String name, String phonenum, String id, boolean available) {
        super(name, phonenum, id);
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}

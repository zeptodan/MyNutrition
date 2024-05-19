package com.example.mynutrition;

public class Nutritionist extends Person{
    boolean available;
    String cnic;
    String institute;
    public Nutritionist() {
        super();
    }

    public Nutritionist(String name, String phonenum, String id, boolean available,String cnic, String institute) {
        super(name, phonenum, id);
        this.available = available;
        this.cnic = cnic;
        this.institute = institute;
    }

    public boolean isAvailable() {
        return available;
    }
}

package com.example.mynutrition;

public class Rider extends Person{
    String cnic;
    String vehiclemodel;
    String vehiclereg;
    public Rider() {
        super();
    }

    public Rider(String name, String phonenum, String id,String cnic,String vehiclemodel,String vehiclereg) {
        super(name, phonenum, id);
        this.cnic = cnic;
        this.vehiclemodel = vehiclemodel;
        this.vehiclereg = vehiclereg;
    }
}

package com.example.database;

public class Supplier {
    int ID;
    String Name;
    long Phone;
    String Email;
    String Address;
    String Scope;

    public Supplier(int ID, String name, long phone, String email, String address, String scope) {
        this.ID = ID;
        Name = name;
        Phone = phone;
        Email = email;
        Address = address;
        Scope = scope;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public long getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getScope() {
        return Scope;
    }
}

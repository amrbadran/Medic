package com.example.database;

public class Category {
    int CategoryID;
    String Name;
    String Description;

    public Category(int categoryID, String name, String description) {
        CategoryID = categoryID;
        Name = name;
        Description = description;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }
}

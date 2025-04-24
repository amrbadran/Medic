package com.example.database;

public class Employee {
    private int SSN;

    private String FName;

    private String MName;

    private String LName;

    private String BirthDate;

    private double SalaryPerHour;

    private String Sex;

    private double bonus;

    public boolean flag_medicineDes;

    public Employee(int SSN, String FName, String MName, String LName, String birthDate, double salaryPerHour, String sex, double bonus) {
        this.SSN = SSN;
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
        BirthDate = birthDate;
        SalaryPerHour = salaryPerHour;
        Sex = sex;
        this.bonus = bonus;
    }

    public int getSSN() {
        return SSN;
    }

    public String getFName() {
        return FName;
    }

    public String getMName() {
        return MName;
    }

    public String getLName() {
        return LName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public double getSalaryPerHour() {
        return SalaryPerHour;
    }

    public String getSex() {
        return Sex;
    }

    public double getBonus() {
        return bonus;
    }


    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}

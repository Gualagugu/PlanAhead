package com.example.myapplication;

public class ClassInfo {
    private String subject;
    private int index;
    private String instructor;
    private double averageGPA;
    private double percentageA;
    private int people;

    public String getSubject() {
        return subject;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getIndex() {
        return index;
    }

    public double getAverageGPA() {
        return averageGPA;
    }

    public double getPercentageA() {
        return percentageA;
    }

    public int getPeople() {
        return people;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setAverageGPA(double averageGPA) {
        this.averageGPA = averageGPA;
    }

    public void setPercentageA(double percentageA) {
        this.percentageA = percentageA;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}

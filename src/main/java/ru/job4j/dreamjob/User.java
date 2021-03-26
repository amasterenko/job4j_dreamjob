package ru.job4j.dreamjob;

public abstract class User {
    private String name;
    private String surName;
    private int age;
    private char sex;

    public User(String name, String surName, int age, char sex) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public char getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}

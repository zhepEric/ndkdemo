package com.example.piaozhe.ndkdemo.bean;

import android.util.SparseArray;

/**
 * @author piaozhe
 * @date 2019/2/18.
 */
public class Person {
    private String name;
    private String age;
    SparseArray sparseArray;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

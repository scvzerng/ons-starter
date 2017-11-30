package com.yazuo.test;

import com.yazuo.intelligent.ons.Key;

public class Person implements Key {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String generateKey() {
        return name;
    }
}

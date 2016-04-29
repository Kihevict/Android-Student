package com.zykj.kihevict.student;

/**
 * Created by Kihevict on 16/4/27.
 */
public class Student {
    private Integer id;
    private String name;
    private String sex;
    private String age;

    public Student()
    {

    }

    public Student(Integer id,String name,String sex,String age)
    {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}

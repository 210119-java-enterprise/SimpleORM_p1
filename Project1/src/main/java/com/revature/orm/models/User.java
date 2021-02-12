package com.revature.orm.models;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.Id;
import com.revature.orm.annotations.Table;


@Table(tableName = "user")
public class User {

    @Id(columnName = "id")
    private int id;

    @Column(columnName = "firstName")
    private String firstName;

    @Column(columnName = "lastName")
    private String lastName;

    @Column(columnName = "age")
    private int age;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}

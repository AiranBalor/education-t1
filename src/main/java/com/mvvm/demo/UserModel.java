package com.mvvm.demo;

import com.mvvm.framework.Model;

public class UserModel extends Model {
    private String firstName;
    private String lastName;
    private String fullName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyObservers("firstName", firstName);
        updateFullName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyObservers("lastName", lastName);
        updateFullName();
    }

    public String getFullName() {
        return fullName;
    }

    private void updateFullName() {
        this.fullName = (firstName != null ? firstName : "") + " " + 
                       (lastName != null ? lastName : "");
        notifyObservers("fullName", fullName.trim());
    }
}
package com.north.phonebook.app.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PhoneBookDTO implements Serializable {

    @NotNull
    @Size(min = 1)
    private String name;

    @Pattern(regexp = "^[+][389]*[7][1256][0-9]{6}$")
    private String phoneNumber;

    public PhoneBookDTO() {
    }

    public PhoneBookDTO(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneBookDTO{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

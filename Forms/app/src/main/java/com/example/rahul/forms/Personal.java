package com.example.rahul.forms;

import com.orm.SugarRecord;

public class Personal extends SugarRecord<Personal>{
    String name;
    String phone;
    String email;
    String password;
    public Personal(){

    }

    public Personal(String name,String phone,String email,String password) {
        super();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }

    public String toString() {
        return "Name - "+this.name+"\nEmail - "+this.email+"\nPhone Number - "+this.phone+"\n";
    }
}

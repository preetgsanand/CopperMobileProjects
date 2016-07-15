package com.example.rahul.forms;

import com.orm.SugarRecord;

/**
 * Created by rahul on 7/6/2016.
 */
public class Additional extends SugarRecord<Additional>{
    private String fatherName;
    private String motherName;
    private String address;
    public Additional() {

    }
    public Additional(String fatherName,String motherName,String address) {
        super();
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
    }
    private String getFatherName() {
        return this.fatherName;
    }
    private String getMotherName() {
        return this.motherName;
    }
    private String getAddress() {
        return this.address;
    }
    public String toString() {
        return "Father's Name - "+this.fatherName+"\nMother's Name - "+this.motherName+"\nAddress - "+this.address;
    }
}

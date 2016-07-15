package com.example.rahul.forms;

import com.orm.SugarRecord;

/**
 * Created by rahul on 7/6/2016.
 */
public class Education extends SugarRecord<Education>{
    String schoolName;
    String schoolCity;
    String schoolState;
    String xMarks;
    String x2Marks;
    public Education() {

    }
    public Education(String schoolName,String schoolCity,String schoolState,String xMarks,String x2Marks){
        super();
        this.schoolName = schoolName;
        this.schoolCity = schoolCity;
        this.schoolState = schoolState;
        this.xMarks = xMarks;
        this.x2Marks = x2Marks;
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public String getSchoolCity() {
        return this.schoolCity;
    }
    public String getSchoolState() {
        return this.schoolState;
    }
    public String getX2Marks() {
        return this.x2Marks;
    }
    public String getxMarks() {
        return this.xMarks;
    }
    public String toString() {
        return "School Name - "+this.schoolName+"\n10th Marks - "+this.xMarks+"\n12th Marks - "+this.x2Marks;
    }
}

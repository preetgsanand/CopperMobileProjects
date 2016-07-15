package com.example.rahul.forms;

import com.orm.SugarRecord;

/**
 * Created by rahul on 7/6/2016.
 */
public class Jee extends SugarRecord<Jee>{
    String entryId;
    String mainRank;
    String mainMarks;
    String advanceRank;
    String advanceMarks;
    public Jee() {

    }
    public Jee(String entryId,
            String mainRank,
            String mainMarks,
            String advanceRank,
            String advanceMarks) {
        super();
        this.entryId = entryId;
        this.mainMarks=mainMarks;
        this.mainRank = mainRank;
        this.advanceMarks = advanceMarks;
        this.advanceRank=advanceRank;
    }
    public String getEntryId() {
        return this.entryId;
    }
    public String getMainRank() {
        return this.mainRank;
    }
    public String getMainMarks() {
        return this.mainMarks;
    }
    public String getAdvanceRank() {
        return this.advanceRank;
    }
    public String getAdvanceMarks() {
        return this.advanceMarks;
    }
    public String toString() {
        return "Entry Id - "+this.entryId+"\nMain Rank - "+this.mainRank+"\nAdvance Rank - "+this.advanceRank;
    }
}

package com.ics26011.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notebook_table")
public class Notebook implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int themeId, numNotes;

    public Notebook() {
        this.name = null;
        this.themeId = -1;
        this.numNotes = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getNumNotes() {
        return numNotes;
    }

    public void setNumNotes(int numNotes) {
        this.numNotes = numNotes;
    }
}
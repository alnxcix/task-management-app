package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "schedule_table")
public class Schedule implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int themeId;

    public Schedule() {
        this.name = null;
        this.themeId = -1;
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
}

package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "class_table", foreignKeys = @ForeignKey(entity = Schedule.class, parentColumns = "id", childColumns = "scheduleId", onDelete = ForeignKey.CASCADE))
public class Class implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String instructors;
    private int themeId, scheduleId;

    public Class(int scheduleId) {
        this.name = null;
        this.instructors = null;
        this.themeId = -1;
        this.scheduleId = scheduleId;
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

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
}
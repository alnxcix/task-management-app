package com.nidoy.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "class_table", foreignKeys = @ForeignKey(entity = Schedule.class, parentColumns = "id", childColumns = "scheduleId", onDelete = ForeignKey.CASCADE), indices = {@Index(value = {"scheduleId"}, unique = true)})
public class Class implements Serializable {

    @NonNull
    @PrimaryKey
    private String name;
    private String instructors, venue;
    private int themeId, scheduleId;

    public Class(int scheduleId) {
        this.name = null;
        this.instructors = null;
        this.venue = null;
        this.themeId = -1;
        this.scheduleId = scheduleId;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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
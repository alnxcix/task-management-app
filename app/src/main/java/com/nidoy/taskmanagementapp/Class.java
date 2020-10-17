package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "class_table")
public class Class implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name, instructors, venue;
    private int themeId;
    private TimeSlot[] timeSlots;

    public Class() {
        this.name = null;
        this.instructors = null;
        this.venue = null;
        this.themeId = -1;
        this.timeSlots = null;
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

    public TimeSlot[] getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(TimeSlot[] timeSlots) {
        this.timeSlots = timeSlots;
    }
}

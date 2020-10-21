package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalTime;

@Entity(tableName = "class_table", foreignKeys = @ForeignKey(entity = Schedule.class, parentColumns = "id", childColumns = "scheduleId", onDelete = ForeignKey.CASCADE))
public class Class implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name, venue, instructors;
    private int dayId, themeId, scheduleId;
    private LocalTime startTime, endTime;

    public Class(int scheduleId) {
        this.name = null;
        this.venue = null;
        this.instructors = null;
        this.dayId = -1;
        this.startTime = null;
        this.endTime = null;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
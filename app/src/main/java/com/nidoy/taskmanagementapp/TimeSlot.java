package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;
import java.time.LocalTime;

@Entity(tableName = "timeSlot_table", foreignKeys = @ForeignKey(entity = Class.class, parentColumns = "name", childColumns = "className", onDelete = ForeignKey.CASCADE))
public class TimeSlot implements Serializable {

    private String className;
    private int dayId;
    private LocalTime startTime, endTime;

    public TimeSlot(String className) {
        this.className = className;
        this.dayId = -1;
        this.startTime = null;
        this.endTime = null;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
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

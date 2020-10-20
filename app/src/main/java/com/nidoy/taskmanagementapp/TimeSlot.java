package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalTime;

@Entity(tableName = "timeSlot_table", foreignKeys = @ForeignKey(entity = Class.class, parentColumns = "id", childColumns = "classId", onDelete = ForeignKey.CASCADE))
public class TimeSlot implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int classId, dayId;
    private LocalTime startTime, endTime;

    public TimeSlot(int classId, int dayId) {
        this.classId = classId;
        this.dayId = dayId;
        this.startTime = null;
        this.endTime = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

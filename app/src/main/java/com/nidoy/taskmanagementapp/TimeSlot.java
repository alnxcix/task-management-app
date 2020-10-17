package com.nidoy.taskmanagementapp;

import java.time.LocalTime;

public class TimeSlot {
    private int dayId;
    private LocalTime startTime, endTime;

    public TimeSlot(int dayId, LocalTime startTime, LocalTime endTime) {
        this.dayId = dayId;
        this.startTime = startTime;
        this.endTime = endTime;
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

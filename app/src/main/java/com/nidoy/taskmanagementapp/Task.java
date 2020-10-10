package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    protected int id;
    protected String label;
    protected String notes;
    protected Date due;
    protected int status;
    protected int legendColor;

    public Task() {
        this.label = null;
        this.notes = null;
        this.due = null;
        this.status = -1;
        this.legendColor = -1;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLegendColor() {
        return legendColor;
    }

    public void setLegendColor(int legendColor) {
        this.legendColor = legendColor;
    }
}

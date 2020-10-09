package com.nidoy.taskmanagementapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    @ColumnInfo(name = "label")
    protected String mLabel;

    @ColumnInfo(name = "notes")
    protected String mNotes;

    @ColumnInfo(name = "due")
    protected Date mDue;

    @ColumnInfo(name = "status")
    protected int mStatusId;

    @ColumnInfo(name = "color")
    protected int mColorId;

    public Task() {
        this.mLabel = null;
        this.mNotes = null;
        this.mDue = null;
        this.mStatusId = -1;
        this.mColorId = -1;
    }

    public int getId() {
        return id;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public Date getmDue() {
        return mDue;
    }

    public void setmDue(Date mDue) {
        this.mDue = mDue;
    }

    public int getmStatusId() {
        return mStatusId;
    }

    public void setmStatusId(int mStatusId) {
        this.mStatusId = mStatusId;
    }

    public int getmColorId() {
        return mColorId;
    }

    public void setmColorId(int mColorId) {
        this.mColorId = mColorId;
    }
}

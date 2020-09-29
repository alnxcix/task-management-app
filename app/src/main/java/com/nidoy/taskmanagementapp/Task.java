package com.nidoy.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    @NonNull
    @ColumnInfo(name = "label")
    protected String mLabel;

    @ColumnInfo(name = "notes")
    protected String mNotes;

    @NonNull
    @ColumnInfo(name = "due")
    protected Date mDue;

    @NonNull
    @ColumnInfo(name = "status")
    protected String mStatus;

    public Task(@NonNull String mLabel, String mNotes, @NonNull Date mDue, @NonNull String mStatus) {
        this.mLabel = mLabel;
        this.mNotes = mNotes;
        this.mDue = mDue;
        this.mStatus = mStatus;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(@NonNull String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    @NonNull
    public Date getmDue() {
        return mDue;
    }

    public void setmDue(@NonNull Date mDue) {
        this.mDue = mDue;
    }

    @NonNull
    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(@NonNull String mStatus) {
        this.mStatus = mStatus;
    }
}

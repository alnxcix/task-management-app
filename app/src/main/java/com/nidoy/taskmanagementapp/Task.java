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

    @NonNull
    @ColumnInfo(name = "status")
    protected String mStatus;

    @ColumnInfo(name = "description")
    protected String mDescription;

    @NonNull
    @ColumnInfo(name = "due")
    protected Date mDue;

    public Task(@NonNull String mLabel, @NonNull String mStatus, String mDescription, @NonNull Date mDue) {
        this.mLabel = mLabel;
        this.mStatus = mStatus;
        this.mDescription = mDescription;
        this.mDue = mDue;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getmLabel() {
        return mLabel;
    }

    @NonNull
    public String getmStatus() {
        return mStatus;
    }

    @NonNull
    public String getmDescription() {
        return mDescription;
    }

    @NonNull
    public Date getmDue() {
        return mDue;
    }

    public void setmLabel(@NonNull String mLabel) {
        this.mLabel = mLabel;
    }

    public void setmStatus(@NonNull String mStatus) {
        this.mStatus = mStatus;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmDue(@NonNull Date mDue) {
        this.mDue = mDue;
    }
}

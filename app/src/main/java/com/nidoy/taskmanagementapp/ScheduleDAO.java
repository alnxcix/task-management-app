package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScheduleDAO {

    @Insert
    void insert(Schedule schedule);

    @Query("SELECT * from schedule_table")
    LiveData<List<Schedule>> getSchedules();

    @Update
    void update(Schedule schedule);

    @Delete
    void delete(Schedule schedule);
}

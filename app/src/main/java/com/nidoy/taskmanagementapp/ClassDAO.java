package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClassDAO {

    @Insert
    void insert(Class c);

    @Query("SELECT * FROM class_table WHERE scheduleId IS :scheduleId AND dayId IS :dayId ORDER BY startTime")
    LiveData<List<Class>> getClasses(int scheduleId, int dayId);

    @Update
    void update(Class c);

    @Delete
    void delete(Class c);
}

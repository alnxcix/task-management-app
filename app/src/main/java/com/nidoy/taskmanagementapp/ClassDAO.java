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

    @Query("SELECT * from class_table ORDER BY name ASC")
    LiveData<List<Class>> getClasses();

    @Query("SELECT * from class_table WHERE scheduleId IS :scheduleId ORDER BY name ASC")
    LiveData<List<Class>> getClassesBySchedule(int scheduleId);

    @Update
    void update(Class c);

    @Delete
    void delete(Class c);
}

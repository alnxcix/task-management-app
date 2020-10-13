package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Query("SELECT * from task_table WHERE statusId IS :statusId ORDER BY due ASC")
    LiveData<List<Task>> getTasksByStatusId(int statusId);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
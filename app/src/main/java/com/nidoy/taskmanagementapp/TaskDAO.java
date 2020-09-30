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

    @Query("SELECT * from task_table WHERE status IS :status ORDER BY due ASC")
    LiveData<List<Task>> getTasksByStatus(String status);

    @Query("SELECT * from task_table WHERE status IS NOT 'Finished' AND due < (strftime('%s','now') * 1000) ORDER BY due ASC")
    LiveData<List<Task>> getOverdueTasks();

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
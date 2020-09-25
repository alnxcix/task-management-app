package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Query("SELECT * from task_table WHERE status IS 'Open' ORDER BY due ASC")
    LiveData<List<Task>> getOpenTasks();

    @Query("SELECT * from task_table WHERE status IS 'Pending'  ORDER BY due ASC")
    LiveData<List<Task>> getPendingTasks();

    @Query("SELECT * from task_table WHERE status IS 'Finished'  ORDER BY due ASC")
    LiveData<List<Task>> getFinishedTasks();

    @Query("SELECT * from task_table WHERE status IS 'Overdue'  ORDER BY due ASC")
    LiveData<List<Task>> getOverdueTasks();

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();
}
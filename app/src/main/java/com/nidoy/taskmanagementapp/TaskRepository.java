package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class TaskRepository {
    private TaskDAO mTaskDAO;

    TaskRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        mTaskDAO = db.taskDAO();
    }

    LiveData<List<Task>> getOpenTasks() {
        return (LiveData<List<Task>>) mTaskDAO.getTasksByStatus("Open");
    }
    LiveData<List<Task>> getPendingTasks() {
        return (LiveData<List<Task>>) mTaskDAO.getTasksByStatus("Pending");
    }
    LiveData<List<Task>> getFinishedTasks() {
        return (LiveData<List<Task>>) mTaskDAO.getTasksByStatus("Finished");
    }

    LiveData<List<Task>> getOverdueTasks() {
        return (LiveData<List<Task>>) mTaskDAO.getTasksByStatus("Overdue");
    }

    void insert(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.insert(task);
        });
    }

    void update(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.update(task);
        });
    }

    void delete(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.delete(task);
        });
    }
}

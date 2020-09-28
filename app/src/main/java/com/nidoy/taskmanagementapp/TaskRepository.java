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
        return mTaskDAO.getTasksByStatus("Open");
    }
    LiveData<List<Task>> getPendingTasks() {
        return mTaskDAO.getTasksByStatus("Pending");
    }
    LiveData<List<Task>> getFinishedTasks() {
        return mTaskDAO.getTasksByStatus("Finished");
    }

    LiveData<List<Task>> getOverdueTasks() {
        return mTaskDAO.getTasksByStatus("Overdue");
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

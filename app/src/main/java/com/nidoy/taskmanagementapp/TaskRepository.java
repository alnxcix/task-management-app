package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class TaskRepository {
    private final TaskDAO taskDAO;

    TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDAO = db.taskDAO();
    }

    LiveData<List<Task>> getTasksByStatusId(int statusId) {
        return taskDAO.getTasksByStatusId(statusId);
    }

    void insert(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDAO.insert(task));
    }

    void update(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDAO.update(task));
    }

    void delete(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDAO.delete(task));
    }
}

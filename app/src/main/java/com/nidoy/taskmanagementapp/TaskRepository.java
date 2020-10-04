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

    LiveData<List<Task>> getTasksByStatus(String status) {
        return mTaskDAO.getTasksByStatus(status);
    }

    void insert(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> mTaskDAO.insert(task));
    }

    void update(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> mTaskDAO.update(task));
    }

    void delete(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> mTaskDAO.delete(task));
    }
}

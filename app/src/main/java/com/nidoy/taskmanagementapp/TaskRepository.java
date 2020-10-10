package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class TaskRepository {
    private TaskDAO taskDAO;

    TaskRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        taskDAO = db.taskDAO();
    }

    LiveData<List<Task>> getTasksByStatusId(int statusId) {
        return taskDAO.getTasksByStatusId(statusId);
    }

    void insert(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> taskDAO.insert(task));
    }

    void update(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> taskDAO.update(task));
    }

    void delete(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> taskDAO.delete(task));
    }
}

package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class TaskRepository {
    private TaskDAO mTaskDAO;
    private LiveData<List<Task>> mOpenTasks, mPendingTasks, mFinishedTasks, mOverdueTasks;

    TaskRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        mTaskDAO = db.taskDAO();
        mOpenTasks = mTaskDAO.getOpenTasks();
        mPendingTasks = mTaskDAO.getPendingTasks();
        mFinishedTasks = mTaskDAO.getFinishedTasks();
        mOverdueTasks = mTaskDAO.getOverdueTasks();
    }

    LiveData<List<Task>> getOpenTasks() {
        return mOpenTasks;
    }

    LiveData<List<Task>> getPendingTasks() {
        return mPendingTasks;
    }

    LiveData<List<Task>> getFinishedTasks() {
        return mFinishedTasks;
    }

    LiveData<List<Task>> getOverdueTasks() {
        return mOverdueTasks;
    }

    void insert(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.insert(task);
        });
    }

    void delete(Task task) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.delete(task);
        });
    }
}

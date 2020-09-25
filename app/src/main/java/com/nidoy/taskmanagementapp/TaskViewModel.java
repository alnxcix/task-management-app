package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository mTaskRepository;
    private LiveData<List<Task>> mOpenTasks, mPendingTasks, mFinishedTasks, mOverdueTasks;

    public TaskViewModel(Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mOpenTasks = mTaskRepository.getOpenTasks();
        mPendingTasks = mTaskRepository.getPendingTasks();
        mFinishedTasks = mTaskRepository.getFinishedTasks();
        mOverdueTasks = mTaskRepository.getOverdueTasks();
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


    public void insert(Task task) {
        mTaskRepository.insert(task);
    }

    public void delete(Task task) {
        mTaskRepository.delete(task);
    }
}

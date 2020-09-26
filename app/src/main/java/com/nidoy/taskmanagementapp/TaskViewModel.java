package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository mTaskRepository;

    public TaskViewModel(Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
    }

    LiveData<List<Task>> getOpenTasks() {
        return (LiveData<List<Task>>) mTaskRepository.getOpenTasks();
    }
    LiveData<List<Task>> getPendingTasks() {
        return (LiveData<List<Task>>) mTaskRepository.getPendingTasks();
    }

    LiveData<List<Task>> getFinishedTasks() {
        return (LiveData<List<Task>>) mTaskRepository.getFinishedTasks();
    }

    LiveData<List<Task>> getOverdueTasks() {
        return (LiveData<List<Task>>) mTaskRepository.getOverdueTasks();
    }

    public void insert(Task task) {
        mTaskRepository.insert(task);
    }

    public void update(Task task) {
        mTaskRepository.update(task);
    }

    public void delete(Task task) {
        mTaskRepository.delete(task);
    }
}

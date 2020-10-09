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

    LiveData<List<Task>> getTaskByStatusId(int statusId) {
        return mTaskRepository.getTasksByStatusId(statusId);
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

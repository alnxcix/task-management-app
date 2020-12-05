package com.ics26011.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository taskRepository;

    public TaskViewModel(Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    LiveData<List<Task>> getTaskByStatusId(int statusId) {
        return taskRepository.getTasksByStatusId(statusId);
    }

    public void insert(Task task) {
        taskRepository.insert(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }
}

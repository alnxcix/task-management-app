package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClassViewModel extends AndroidViewModel {
    private final ClassRepository classRepository;

    public ClassViewModel(Application application) {
        super(application);
        classRepository = new ClassRepository(application);
    }

    LiveData<List<Class>> getClassesByScheduleId(final int scheduleId) {
        return classRepository.getClassesByScheduleId(scheduleId);
    }

    public void insert(Class c) {
        classRepository.insert(c);
    }

    public void update(Class c) {
        classRepository.update(c);
    }

    public void delete(Class c) {
        classRepository.delete(c);
    }

}

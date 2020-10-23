package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClassRepository {
    private final ClassDAO classDAO;

    ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDAO = db.classDAO();
    }

    LiveData<List<Class>> getClasses(int scheduleId, int dayId) {
        return classDAO.getClasses(scheduleId, dayId);
    }

    void insert(Class c) {
        AppDatabase.databaseWriteExecutor.execute(() -> classDAO.insert(c));
    }

    void update(Class c) {
        AppDatabase.databaseWriteExecutor.execute(() -> classDAO.update(c));
    }

    void delete(Class c) {
        AppDatabase.databaseWriteExecutor.execute(() -> classDAO.delete(c));
    }
}

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

    LiveData<List<Class>> getClasses() {
        return classDAO.getClasses();
    }

    LiveData<List<Class>> getClassesByScheduleId(final int scheduleId) {
        return classDAO.getClassesBySchedule(scheduleId);
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

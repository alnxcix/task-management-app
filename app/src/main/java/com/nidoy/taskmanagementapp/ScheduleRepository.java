package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleRepository {
    private final ScheduleDAO scheduleDAO;

    ScheduleRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        scheduleDAO = db.scheduleDAO();
    }

    LiveData<List<Schedule>> getSchedules() {
        return scheduleDAO.getSchedules();
    }

    void insert(Schedule schedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduleDAO.insert(schedule));
    }

    void update(Schedule schedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduleDAO.update(schedule));
    }

    void delete(Schedule schedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduleDAO.delete(schedule));
    }
}

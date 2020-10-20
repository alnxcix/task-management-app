package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeSlotRepository {
    private final TimeSlotDAO timeSlotDAO;

    TimeSlotRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        timeSlotDAO = db.timeSlotDAO();
    }

    LiveData<List<TimeSlot>> getClassTimeSlots(int classId) {
        return timeSlotDAO.getClassTimeSlots(classId);
    }

    void insert(TimeSlot timeSlot) {
        AppDatabase.databaseWriteExecutor.execute(() -> timeSlotDAO.insert(timeSlot));
    }

    void update(TimeSlot timeSlot) {
        AppDatabase.databaseWriteExecutor.execute(() -> timeSlotDAO.update(timeSlot));
    }

    void delete(TimeSlot timeSlot) {
        AppDatabase.databaseWriteExecutor.execute(() -> timeSlotDAO.delete(timeSlot));
    }

}

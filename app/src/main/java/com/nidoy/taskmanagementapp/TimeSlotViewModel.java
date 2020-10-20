package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeSlotViewModel extends AndroidViewModel {
    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotViewModel(Application application) {
        super(application);
        timeSlotRepository = new TimeSlotRepository(application);
    }

    LiveData<List<TimeSlot>> getClassTimeSlots(int classId) {
        return timeSlotRepository.getClassTimeSlots(classId);
    }

    public void insert(TimeSlot timeSlot) {
        timeSlotRepository.insert(timeSlot);
    }

    public void update(TimeSlot timeSlot) {
        timeSlotRepository.update(timeSlot);
    }

    public void delete(TimeSlot timeSlot) {
        timeSlotRepository.delete(timeSlot);
    }

}

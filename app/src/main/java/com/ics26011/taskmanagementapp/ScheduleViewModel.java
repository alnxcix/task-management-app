package com.ics26011.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {
    private final ScheduleRepository scheduleRepository;

    public ScheduleViewModel(Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
    }

    LiveData<List<Schedule>> getSchedulesSortedByName() {
        return scheduleRepository.getSchedulesSortedByName();
    }

    public void insert(Schedule schedule) {
        scheduleRepository.insert(schedule);
    }

    public void update(Schedule schedule) {
        scheduleRepository.update(schedule);
    }

    public void delete(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

}

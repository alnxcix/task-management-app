package com.nidoy.taskmanagementapp;

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

    LiveData<List<Schedule>> getSortedSchedulesBy(String prop) {
        return scheduleRepository.getSortedSchedulesBy(prop);
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

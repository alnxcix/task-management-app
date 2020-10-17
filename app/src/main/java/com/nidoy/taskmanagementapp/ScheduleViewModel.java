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

    LiveData<List<Schedule>> getSchedules() {
        return scheduleRepository.getSchedules();
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

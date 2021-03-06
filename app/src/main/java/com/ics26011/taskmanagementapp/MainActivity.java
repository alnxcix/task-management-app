package com.ics26011.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static final int CREATE_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TASK_ACTIVITY_REQUEST_CODE = 2;
    public static final int CREATE_NOTEBOOK_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_NOTEBOOK_ACTIVITY_REQUEST_CODE = 4;
    public static final int CREATE_SCHEDULE_ACTIVITY_REQUEST_CODE = 5;
    public static final int UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE = 6;
    public static TaskViewModel taskViewModel;
    public static NotebookViewModel notebookViewModel;
    public static ScheduleViewModel scheduleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize UI elements
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        // Setup
        bottomNavigationView.setOnNavigationItemReselectedListener(v -> {
        });
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            taskViewModel.insert((Task) data.getSerializableExtra(TaskFormActivity.EXTRA_REPLY));
        else if (requestCode == UPDATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            taskViewModel.update((Task) data.getSerializableExtra(TaskFormActivity.EXTRA_REPLY));
        else if (requestCode == CREATE_NOTEBOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            notebookViewModel.insert((Notebook) data.getSerializableExtra(NotebookFormActivity.EXTRA_REPLY));
        else if (requestCode == UPDATE_NOTEBOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            notebookViewModel.update((Notebook) data.getSerializableExtra(NotebookFormActivity.EXTRA_REPLY));
        else if (requestCode == CREATE_SCHEDULE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            scheduleViewModel.insert((Schedule) data.getSerializableExtra(ScheduleFormActivity.EXTRA_REPLY));
        else if (requestCode == UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            scheduleViewModel.update((Schedule) data.getSerializableExtra(ScheduleFormActivity.EXTRA_REPLY));
    }
}
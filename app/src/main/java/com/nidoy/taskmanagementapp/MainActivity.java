package com.nidoy.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Request codes
    public static final int CREATE_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TASK_ACTIVITY_REQUEST_CODE = 2;

    // Task view models
    public static TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        // Handling multiple taps by the user on the same navigation item
        bottomNavigationView.setOnNavigationItemReselectedListener(v -> {
        });

        // Enable action bar
        NavigationUI.setupActionBarWithNavController(this, navController, new AppBarConfiguration.Builder(R.id.tasksFragment, R.id.notesFragment, R.id.scheduleFragment).build());

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(TaskActivity.EXTRA_REPLY);
            mTaskViewModel.insert(task);
            Toast.makeText(this, getString(R.string.toast_task_saved), Toast.LENGTH_SHORT).show();
        } else if (requestCode == UPDATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(TaskActivity.EXTRA_REPLY);
            mTaskViewModel.update(task);
            Toast.makeText(this, getString(R.string.toast_changes_saved), Toast.LENGTH_SHORT).show();
        }
    }
}
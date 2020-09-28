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
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int MODIFY_TASK_ACTIVITY_REQUEST_CODE = 2;

    // Task view models
    public static TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Switch between different fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener((item) -> { /* Do nothing when the user double taps on a menu item */ });
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        // Enable action bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.tasksFragment, R.id.notesFragment, R.id.scheduleFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(NewTaskActivity.EXTRA_REPLY);
            mTaskViewModel.insert(task);
            Toast.makeText(this, "Saved. Req: " + requestCode + ", Res: " + resultCode, Toast.LENGTH_SHORT).show();
        } else if (requestCode == MODIFY_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(NewTaskActivity.EXTRA_REPLY);
            mTaskViewModel.update(task);
            Toast.makeText(this, "Saved. Req: " + requestCode + ", Res: " + resultCode, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Not saved. Req: " + requestCode + ", Res: " + resultCode, Toast.LENGTH_SHORT).show();
    }
}
package com.nidoy.taskmanagementapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Switch between different fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener((item) -> { /* Do nothing when the user double taps on a menu item */ });
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        // To enable action bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.tasksFragment, R.id.notesFragment, R.id.scheduleFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
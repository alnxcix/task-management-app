package com.nidoy.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ScheduleIndividualActivity extends AppCompatActivity {

    private static final int CREATE_CLASS_ACTIVITY_REQUEST_CODE = 1;
    private static final int UPDATE_CLASS_ACTIVITY_REQUEST_CODE = 2;
    private static Schedule schedule;
    public static ClassViewModel classViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_individual);
        // Initialize UI elements and variables
        schedule = (Schedule) getIntent().getSerializableExtra("schedule");
        assert schedule != null;
        int dynamicColor = schedule.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        PorterDuffColorFilter dynamicColorFilter = new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        ExtendedFloatingActionButton btnNew = findViewById(R.id.btnNew);
        // Setup
        Objects.requireNonNull(topAppBar.getNavigationIcon()).setColorFilter(dynamicColorFilter);
        topAppBar.setTitle(schedule.getName());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.setBackgroundColor(schedule.getThemeId());
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        tabLayout.setBackgroundColor(schedule.getThemeId());
        tabLayout.setTabTextColors(ColorUtils.setAlphaComponent(dynamicColor, 154), dynamicColor);
        btnNew.getIcon().setColorFilter(dynamicColorFilter);
        btnNew.setTextColor(dynamicColor);
        btnNew.setBackgroundColor(schedule.getThemeId());
        viewPager2.setAdapter(new ClassesPagerAdapter(this, schedule.getId()));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText(getResources().getString(R.string.sun));
                    break;
                }
                case 1: {
                    tab.setText(getResources().getString(R.string.mon));
                    break;
                }
                case 2: {
                    tab.setText(getResources().getString(R.string.tue));
                    break;
                }
                case 3: {
                    tab.setText(getResources().getString(R.string.wed));
                    break;
                }
                case 4: {
                    tab.setText(getResources().getString(R.string.thu));
                    break;
                }
                case 5: {
                    tab.setText(getResources().getString(R.string.fri));
                    break;
                }
                case 6: {
                    tab.setText(getResources().getString(R.string.sat));
                    break;
                }
            }
        }).attach();
        // Event listeners
        btnNew.setOnClickListener(v -> startActivityForResult(new Intent(this, ClassFormActivity.class).putExtra("class", new Class(schedule.getId())), CREATE_CLASS_ACTIVITY_REQUEST_CODE));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_CLASS_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            classViewModel.insert((Class) data.getSerializableExtra(ClassFormActivity.EXTRA_REPLY));
            schedule.setNumClasses(schedule.getNumClasses() + 1);
            MainActivity.scheduleViewModel.update(schedule);
        }
    }
}
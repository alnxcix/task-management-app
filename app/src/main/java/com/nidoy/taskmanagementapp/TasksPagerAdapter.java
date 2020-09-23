package com.nidoy.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TasksPagerAdapter extends FragmentStateAdapter {

    public TasksPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OpenTasksFragment();
            case 1:
                return new PendingTasksFragment();
            case 2:
                return new DoneTasksFragment();
            default:
                return new OverdueTasksFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
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
                return TasksFragment.newInstance("Open");
            case 1:
                return TasksFragment.newInstance("Pending");
            default:
                return TasksFragment.newInstance("Finished");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
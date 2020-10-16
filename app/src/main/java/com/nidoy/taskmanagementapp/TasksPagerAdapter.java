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
                return TasksFragment.newInstance(R.string.open);
            case 1:
                return TasksFragment.newInstance(R.string.pending);
            default:
                return TasksFragment.newInstance(R.string.finished);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
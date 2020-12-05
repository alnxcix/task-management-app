package com.nidoy.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ClassesPagerAdapter extends FragmentStateAdapter {
    private final int scheduleId;

    public ClassesPagerAdapter(@NonNull FragmentActivity fragmentActivity, int scheduleId) {
        super(fragmentActivity);
        this.scheduleId = scheduleId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ClassesFragment.newInstance(scheduleId, position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}

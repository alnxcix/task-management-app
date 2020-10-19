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
        switch (position) {
            case 0:
                return ClassesFragment.newInstance(scheduleId, R.string.sun);
            case 1:
                return ClassesFragment.newInstance(scheduleId, R.string.mon);
            case 2:
                return ClassesFragment.newInstance(scheduleId, R.string.tue);
            case 3:
                return ClassesFragment.newInstance(scheduleId, R.string.wed);
            case 4:
                return ClassesFragment.newInstance(scheduleId, R.string.thu);
            case 5:
                return ClassesFragment.newInstance(scheduleId, R.string.fri);
            default:
                return ClassesFragment.newInstance(scheduleId, R.string.sat);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}

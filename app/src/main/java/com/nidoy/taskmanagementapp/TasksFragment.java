package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Date;

public class TasksFragment extends Fragment {

    public TasksFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize variables
        ExtendedFloatingActionButton btnFAB = requireActivity().findViewById(R.id.btnFAB);
        ViewPager2 viewPager2 = requireActivity().findViewById(R.id.viewPager);
        TabLayout tabLayout = requireActivity().findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    setTab(tab, R.string.open, R.drawable.ic_wb_incandescent_24px);
                    break;
                }
                case 1: {
                    setTab(tab, R.string.pending, R.drawable.ic_hourglass_empty_black_24dp);
                    break;
                }
                case 2: {
                    setTab(tab, R.string.finished, R.drawable.ic_done_all_24px);
                    break;
                }
                case 3: {
                    setTab(tab, R.string.overdue, R.drawable.ic_error_outline_black_24dp);
                    break;
                }
            }
        });

        btnFAB.setText(getResources().getString(R.string.task));
        viewPager2.setAdapter(new TasksPagerAdapter(this.requireActivity()));
        tabLayoutMediator.attach();

        // Add event listeners
        btnFAB.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), NewTaskActivity.class);
            intent.putExtra("task", new Task("", "Open", "", new Date()));
            requireActivity().startActivityForResult(intent, MainActivity.NEW_TASK_ACTIVITY_REQUEST_CODE);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setTab(TabLayout.Tab tab, int label, int icon) {
        tab.setText(getResources().getString(label));
        tab.setIcon(getResources().getDrawable(icon));
    }
}
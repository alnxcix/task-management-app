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

public class TasksMainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_main, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements and variables
        TabLayout tabLayout = requireActivity().findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = requireActivity().findViewById(R.id.viewPager);
        ExtendedFloatingActionButton btnNew = this.requireActivity().findViewById(R.id.btnNew);
        // Setup
        viewPager2.setAdapter(new TasksPagerAdapter(this.requireActivity()));
        btnNew.setText(R.string.task);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(getResources().getStringArray(R.array.arr_task_status)[position])).attach();
        // Event listeners
        btnNew.setOnClickListener(v -> requireActivity().startActivityForResult(new Intent(view.getContext(), TaskFormActivity.class), MainActivity.CREATE_TASK_ACTIVITY_REQUEST_CODE));
    }
}
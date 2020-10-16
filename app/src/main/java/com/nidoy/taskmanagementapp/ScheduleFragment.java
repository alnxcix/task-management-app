package com.nidoy.taskmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Date;

public class ScheduleFragment extends Fragment {

    public ScheduleFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ExtendedFloatingActionButton
        ExtendedFloatingActionButton btnNew = requireActivity().findViewById(R.id.btnNew);
        btnNew.setText(R.string.schedule);
        btnNew.setOnClickListener(v -> {
            // TODO Navigate to NewScheduleActivity
            requireActivity().startActivityForResult(new Intent(view.getContext(), ScheduleActivity.class).putExtra("sched", new Task("", "", new Date(), "")), MainActivity.CREATE_TASK_ACTIVITY_REQUEST_CODE);
        });
    }
}
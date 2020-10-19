package com.nidoy.taskmanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesFragment extends Fragment {

    private static final String ARG_SCHEDULE_ID = "scheduleId";
    private static final String ARG_DAY_ID = "dayId";

    private int scheduleId, dayId;

    public static ClassesFragment newInstance(int scheduleId, int dayId) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCHEDULE_ID, scheduleId);
        args.putInt(ARG_DAY_ID, dayId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleId = getArguments() == null ? -1 : getArguments().getInt(ARG_SCHEDULE_ID);
        dayId = getArguments() == null ? -1 : getArguments().getInt(ARG_DAY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ClassListAdapter adapter = new ClassListAdapter(view.getContext());
        // Setup
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ScheduleIndividualActivity.classViewModel = new ViewModelProvider(requireActivity()).get(ClassViewModel.class);
        ScheduleIndividualActivity.classViewModel.getClassesByScheduleId(scheduleId).observe(getViewLifecycleOwner(), adapter::setClasses);
    }
}
package com.nidoy.taskmanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TasksFragment extends Fragment {

    private static final String ARG_STATUS_ID = "statusId";

    private int statusId;

    public static TasksFragment newInstance(int statusId) {
        final TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATUS_ID, statusId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusId = (getArguments() == null ? -1 : getArguments().getInt(ARG_STATUS_ID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements and variables
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final TaskListAdapter adapter = new TaskListAdapter(view.getContext());

        // Setup
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MainActivity.taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        // Update the cached copy of the tasks in the adapter.
        MainActivity.taskViewModel.getTaskByStatusId(statusId).observe(getViewLifecycleOwner(), adapter::setTasks);
    }
}
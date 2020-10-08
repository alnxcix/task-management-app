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

    private static final String ARG_STATUS = "status";

    private String mStatus;

    public static TasksFragment newInstance(String status) {
        final TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = (getArguments() == null ? null : getArguments().getString(ARG_STATUS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize variables
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final TaskListAdapter adapter = new TaskListAdapter(view.getContext());

        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MainActivity.mTaskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        // Update the cached copy of the tasks in the adapter.
        MainActivity.mTaskViewModel.getTaskByStatus(mStatus).observe(getViewLifecycleOwner(), adapter::setTasks);
    }
}
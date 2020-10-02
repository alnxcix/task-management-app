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

public class OpenTasksFragment extends Fragment {

    public OpenTasksFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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
        MainActivity.mTaskViewModel.getOpenTasks().observe(getViewLifecycleOwner(), adapter::setTasks);
    }
}
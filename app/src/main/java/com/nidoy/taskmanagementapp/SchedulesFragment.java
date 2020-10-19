package com.nidoy.taskmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SchedulesFragment extends Fragment {

    public SchedulesFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements and variables
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ScheduleListAdapter adapter = new ScheduleListAdapter(view.getContext());
        final ExtendedFloatingActionButton btnNew = requireActivity().findViewById(R.id.btnNew);
        // Setup
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        btnNew.setText(R.string.schedule);
        // Event listeners
        btnNew.setOnClickListener(v -> requireActivity().startActivityForResult(new Intent(view.getContext(), ScheduleFormActivity.class), MainActivity.CREATE_SCHEDULE_ACTIVITY_REQUEST_CODE));

        MainActivity.scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        // Update the cached copy of the schedules in the adapter.
        MainActivity.scheduleViewModel.getSortedSchedulesBy("name").observe(getViewLifecycleOwner(), adapter::setSchedules);
    }
}
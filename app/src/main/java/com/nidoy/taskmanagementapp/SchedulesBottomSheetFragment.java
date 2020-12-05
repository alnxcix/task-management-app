package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SchedulesBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String ARG_SCHEDULE = "schedule";

    private Schedule schedule;

    public static SchedulesBottomSheetFragment newInstance(Schedule schedule) {
        final SchedulesBottomSheetFragment fragment = new SchedulesBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHEDULE, schedule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schedule = getArguments() == null ? null : (Schedule) getArguments().getSerializable(ARG_SCHEDULE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedules_bottom_sheet, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        final TextView txtHeading = view.findViewById(R.id.txtHeading);
        final TextView txtSubHeading = view.findViewById(R.id.txtSubHeading);
        // Setup
        txtHeading.setText(schedule.getName());
        txtHeading.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(schedule.getThemeId(), PorterDuff.Mode.SRC_IN));
        txtSubHeading.setText(schedule.getNumClasses() + " " + getString(R.string.txt_classes));
        view.findViewById(R.id.txtOpen).setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity(), ScheduleIndividualActivity.class).putExtra("schedule", schedule));
            this.dismiss();
        });
        view.findViewById(R.id.txtEdit).setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), ScheduleFormActivity.class).putExtra("schedule", schedule), MainActivity.UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        view.findViewById(R.id.txtDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(view.getContext())
                .setTitle(getString(R.string.dialog_title_delete_schedule))
                .setMessage(getString(R.string.dialog_message_delete_schedule))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    MainActivity.scheduleViewModel.delete(schedule);
                    this.dismiss();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
    }
}
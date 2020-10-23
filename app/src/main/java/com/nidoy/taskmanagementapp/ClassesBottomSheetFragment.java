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

public class ClassesBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_CLASS = "class";

    private Class c;

    public static ClassesBottomSheetFragment newInstance(Class c) {
        ClassesBottomSheetFragment fragment = new ClassesBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLASS, c);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getArguments() == null ? null : (Class) getArguments().getSerializable(ARG_CLASS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes_bottom_sheet, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        final TextView txtTime = view.findViewById(R.id.txtTime);
        final TextView txtHours = view.findViewById(R.id.txtHours);
        final TextView txtName = view.findViewById(R.id.txtName);
        final TextView txtVenue = view.findViewById(R.id.txtVenue);
        final TextView txtInstructors = view.findViewById(R.id.txtInstructors);
        // Setup
        txtTime.setText(c.getStartTime().toString() + " - " + c.getEndTime().toString());
        txtHours.setText(c.getEndTime().getHour() - c.getStartTime().getHour() + " " + this.getString(R.string.hours) + ", " + Math.abs(c.getEndTime().getMinute() - c.getStartTime().getMinute()) + " " + this.getString(R.string.minutes));
        txtName.setText(c.getName());
        txtName.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(c.getThemeId(), PorterDuff.Mode.SRC_IN));
        txtInstructors.setVisibility(c.getInstructors() == null ? View.GONE : View.VISIBLE);
        txtInstructors.setText(c.getInstructors());
        txtVenue.setVisibility(c.getVenue() == null ? View.GONE : View.VISIBLE);
        txtVenue.setText(c.getVenue());
        // Event listeners
        view.findViewById(R.id.txtEdit).setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), ClassFormActivity.class).putExtra("class", c), ScheduleIndividualActivity.UPDATE_CLASS_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        view.findViewById(R.id.txtDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(view.getContext())
                .setTitle(getString(R.string.dialog_title_delete_class))
                .setMessage(getString(R.string.dialog_message_delete_class))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    ScheduleIndividualActivity.classViewModel.delete(c);
                    this.dismiss();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
    }
}
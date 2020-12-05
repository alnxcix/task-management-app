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
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TasksBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String ARG_TASK = "task";

    private Task task;

    public static TasksBottomSheetFragment newInstance(Task task) {
        final TasksBottomSheetFragment fragment = new TasksBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = getArguments() == null ? null : (Task) getArguments().getSerializable(ARG_TASK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks_bottom_sheet, container, false);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        final TextView txtLabel = view.findViewById(R.id.txtLabel);
        final Chip chipOpen = view.findViewById(R.id.chipOpen);
        final Chip chipPending = view.findViewById(R.id.chipPending);
        final Chip chipFinished = view.findViewById(R.id.chipFinished);
        // Setup
        txtLabel.setText(task.getLabel());
        txtLabel.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(task.getThemeId(), PorterDuff.Mode.SRC_IN));
        ((TextView) view.findViewById(R.id.txtDueDate)).setText(task.getDue().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
        ((TextView) view.findViewById(R.id.txtTime)).setText(task.getDue().format(DateTimeFormatter.ofPattern("hh:mm a")));
        ((TextView) view.findViewById(R.id.txtFinish)).setText(task.getStatusId() == 2 ? getString(R.string.finished) + " " + task.getFinish().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")) : null);
        view.findViewById(R.id.txtFinish).setVisibility(task.getStatusId() == 2 ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.txtOverdue).setVisibility(task.getDue().isBefore(LocalDateTime.now()) && task.getStatusId() != 2 ? View.VISIBLE : View.GONE);
        chipOpen.setChecked(task.getStatusId() == 0);
        chipPending.setChecked(task.getStatusId() == 1);
        chipFinished.setChecked(task.getStatusId() == 2);
        chipOpen.setCheckable(task.getStatusId() == 0);
        chipPending.setCheckable(task.getStatusId() == 1);
        chipFinished.setCheckable(task.getStatusId() == 2);
        // Add event listeners
        chipOpen.setOnClickListener(v -> {
            if (task.getStatusId() != 0) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getString(R.string.dialog_open_task))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            task.setStatusId(0);
                            MainActivity.taskViewModel.update(task);
                            this.dismiss();
                        }).setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        });
        chipPending.setOnClickListener(v -> {
            if (task.getStatusId() != 1) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getString(R.string.dialog_pending_task))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            task.setStatusId(1);
                            MainActivity.taskViewModel.update(task);
                            this.dismiss();
                        }).setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        });
        chipFinished.setOnClickListener(v -> {
            if (task.getStatusId() != 2) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getString(R.string.dialog_finished_task))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            task.setStatusId(2);
                            task.setFinish(LocalDateTime.now());
                            MainActivity.taskViewModel.update(task);
                            this.dismiss();
                        }).setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        });
        view.findViewById(R.id.txtOpen).setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity(), TaskIndividualActivity.class).putExtra("task", task));
            this.dismiss();
        });
        view.findViewById(R.id.txtEdit).setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), TaskFormActivity.class).putExtra("task", task), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        view.findViewById(R.id.txtDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(getContext())
                .setTitle(getString(R.string.dialog_title_delete_task))
                .setMessage(getString(R.string.dialog_message_delete_task))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    MainActivity.taskViewModel.delete(task);
                    this.dismiss();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
    }
}
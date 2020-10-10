package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        task = (getArguments() == null ? null : (Task) getArguments().getSerializable(ARG_TASK));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks_bottom_sheet, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Chip chipOpen = view.findViewById(R.id.chipOpen);
        final Chip chipPending = view.findViewById(R.id.chipPending);
        final Chip chipFinished = view.findViewById(R.id.chipFinished);
        final ImageView imgOverdue = view.findViewById(R.id.imgOverdue);
        final TextView txtLabel = view.findViewById(R.id.txtLabel);
        final TextView txtDueDate = view.findViewById(R.id.txtDueDate);
        final TextView txtTime = view.findViewById(R.id.txtTime);
        final TextView txtOverdue = view.findViewById(R.id.txtOverdue);
        final TextView txtOpen = view.findViewById(R.id.txtOpen);
        final TextView txtEdit = view.findViewById(R.id.txtEdit);
        final TextView txtDelete = view.findViewById(R.id.txtDelete);

        txtLabel.setText(task.getLabel());
        txtLabel.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(task.getLegendColor(), PorterDuff.Mode.SRC_IN));
        txtDueDate.setText(new SimpleDateFormat("MMM. dd, yyyy").format(task.getDue()));
        txtTime.setText(new SimpleDateFormat("hh:mm aa").format(task.getDue()));
        chipOpen.setChecked(task.getStatus() == R.string.open);
        chipPending.setChecked(task.getStatus() == R.string.pending);
        chipFinished.setChecked(task.getStatus() == R.string.finished);
        chipOpen.setCheckable(task.getStatus() == R.string.open);
        chipPending.setCheckable(task.getStatus() == R.string.pending);
        chipFinished.setCheckable(task.getStatus() == R.string.finished);

        // Handle overdue indicators
        imgOverdue.setVisibility(task.getDue().getTime() <= new Date().getTime() && task.getStatus() != R.string.finished ? View.VISIBLE : View.GONE);
        txtOverdue.setVisibility(task.getDue().getTime() <= new Date().getTime() && task.getStatus() != R.string.finished ? View.VISIBLE : View.GONE);

        chipOpen.setOnClickListener(v -> {
            if (task.getStatus() != R.string.open) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_open_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    task.setStatus(R.string.open);
                    MainActivity.taskViewModel.update(task);
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        chipPending.setOnClickListener(v -> {
            if (task.getStatus() != R.string.pending) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_pending_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    task.setStatus(R.string.pending);
                    MainActivity.taskViewModel.update(task);
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        chipFinished.setOnClickListener(v -> {
            if (task.getStatus() != R.string.finished) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_finished_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    task.setStatus(R.string.finished);
                    MainActivity.taskViewModel.update(task);
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        txtOpen.setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity(), TaskIndividualActivity.class).putExtra("task", task));
            this.dismiss();
        });
        txtEdit.setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), TaskFormActivity.class).putExtra("task", task), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        txtDelete.setOnClickListener(v -> new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_delete_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
            MainActivity.taskViewModel.delete(task);
            this.dismiss();
        }).setNegativeButton(requireContext().getText(R.string.no), null).create().show());
    }
}
package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    List<Task> mTasks; // Cached copy of tasks

    TaskListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = mTasks.get(position);

        // Set data
        holder.txtLabel.setText(current.getmLabel());
        holder.txtDueDate.setText(Utils.dateFormat.format(current.getmDue()));
        holder.txtTime.setText(Utils.timeFormat.format(current.getmDue()));
        holder.txtNotes.setText(current.getmNotes());
        holder.txtNotes.setVisibility(TextUtils.isEmpty(current.getmNotes()) ? View.GONE : View.VISIBLE);
        holder.chipOpen.setChecked(current.getmStatus().equals(mContext.getString(R.string.open)));
        holder.chipOpen.setCheckable(current.getmStatus().equals(mContext.getString(R.string.open)));
        holder.chipPending.setChecked(current.getmStatus().equals(mContext.getString(R.string.pending)));
        holder.chipPending.setCheckable(current.getmStatus().equals(mContext.getString(R.string.pending)));
        holder.chipFinished.setChecked(current.getmStatus().equals(mContext.getString(R.string.finished)));
        holder.chipFinished.setCheckable(current.getmStatus().equals(mContext.getString(R.string.finished)));

        // Event listeners
        holder.chipOpen.setOnClickListener(v -> new AlertDialog.Builder(mContext).setMessage(mContext.getText(R.string.prompt_open_task)).setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
            current.setmStatus(mContext.getString(R.string.open));
            MainActivity.mTaskViewModel.update(current);
            Toast.makeText(mContext, mContext.getText(R.string.toast_task_open), Toast.LENGTH_SHORT).show();
        }).setNegativeButton(mContext.getText(R.string.no), null).create().show());

        holder.chipPending.setOnClickListener(v -> new AlertDialog.Builder(mContext).setMessage(mContext.getText(R.string.prompt_pending_task)).setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
            current.setmStatus(mContext.getString(R.string.pending));
            MainActivity.mTaskViewModel.update(current);
            Toast.makeText(mContext, mContext.getText(R.string.toast_task_pending), Toast.LENGTH_SHORT).show();
        }).setNegativeButton(mContext.getText(R.string.no), null).create().show());

        holder.chipFinished.setOnClickListener(v -> new AlertDialog.Builder(mContext).setMessage(mContext.getText(R.string.prompt_finished_task)).setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
            current.setmStatus(mContext.getString(R.string.finished));
            MainActivity.mTaskViewModel.update(current);
            Toast.makeText(mContext, mContext.getText(R.string.toast_task_finished), Toast.LENGTH_SHORT).show();
        }).setNegativeButton(mContext.getText(R.string.no), null).create().show());

        holder.btnEdit.setOnClickListener(v -> ((Activity) mContext).startActivityForResult(new Intent(mContext, TaskActivity.class).putExtra("task", current), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE));
        holder.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(mContext).setMessage(mContext.getText(R.string.prompt_delete_task)).setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
            MainActivity.mTaskViewModel.delete(current);
            Toast.makeText(mContext, mContext.getText(R.string.toast_task_deleted), Toast.LENGTH_SHORT).show();
        }).setNegativeButton(mContext.getText(R.string.no), null).create().show());
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(mInflater.inflate(R.layout.task_list_item, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getItemCount() is called many times, and when it is first called,
    // mTasks has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final Chip chipOpen, chipPending, chipFinished;
        private final ImageButton btnDelete, btnEdit;
        private final TextView txtDueDate, txtLabel, txtNotes, txtTime;

        private TaskViewHolder(View itemView) {
            super(itemView);
            // UI elements
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            chipFinished = itemView.findViewById(R.id.chipFinished);
            chipOpen = itemView.findViewById(R.id.chipOpen);
            chipPending = itemView.findViewById(R.id.chipPending);
            txtNotes = itemView.findViewById(R.id.txtNotes);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
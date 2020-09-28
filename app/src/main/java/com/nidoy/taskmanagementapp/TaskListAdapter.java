package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat("MMM. dd (h:mm aa)");
    private final LayoutInflater mInflater;
    private final Context mContext;
    List<Task> mTasks; // Cached copy of tasks

    void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    TaskListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = mTasks.get(position);

        // Data
        holder.task_heading.setText(current.getmLabel());
        holder.task_subheading.setText(mContext.getResources().getString(R.string.due_on) + " " + formatter.format(current.getmDue()));
        holder.task_description.setText(current.getmDescription());
        holder.chipOpen.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.open)));
        holder.chipPending.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.pending)));
        holder.chipFinished.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.finished)));

        // Event listeners
        holder.chipOpen.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_open_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.open));
                MainActivity.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_open), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });

        holder.chipPending.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_pending_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.pending));
                MainActivity.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_pending), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });

        holder.chipFinished.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_finished_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.finished));
                MainActivity.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_finished), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NewTaskActivity.class);
            intent.putExtra("task", current);
            ((Activity) mContext).startActivityForResult(intent, MainActivity.MODIFY_TASK_ACTIVITY_REQUEST_CODE);
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_delete_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                MainActivity.mTaskViewModel.delete(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_deleted), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        // Declare UI elements
        private final Chip chipOpen, chipPending, chipFinished;

        private final ImageButton btnEdit, btnDelete;
        private final TextView task_heading, task_subheading, task_description;

        private TaskViewHolder(View itemView) {
            super(itemView);
            // Initialize UI elements
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            chipFinished = itemView.findViewById(R.id.chipFinished);
            chipOpen = itemView.findViewById(R.id.chipOpen);
            chipPending = itemView.findViewById(R.id.chipPending);
            task_description = itemView.findViewById(R.id.task_description);
            task_heading = itemView.findViewById(R.id.task_heading);
            task_subheading = itemView.findViewById(R.id.task_subheading);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }
}
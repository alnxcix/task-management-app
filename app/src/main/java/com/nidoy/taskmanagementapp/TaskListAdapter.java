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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
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

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = mTasks.get(position);
        holder.txtLabel.setText(current.getmLabel());
        holder.txtDueDate.setText(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()));
        holder.txtTime.setText(new SimpleDateFormat("hh:mm aa").format(current.getmDue()));
        holder.txtNotes.setText(current.getmNotes());
        holder.txtNotes.setVisibility(TextUtils.isEmpty(current.getmNotes()) ? View.GONE : View.VISIBLE);
        holder.btnExpand.setOnClickListener(v -> {
            holder.expanded.setVisibility(holder.expanded.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            holder.btnExpand.setImageResource(holder.expanded.getVisibility() == View.GONE ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
        });
        holder.btnEdit.setOnClickListener(v -> ((Activity) mContext).startActivityForResult(new Intent(mContext, TaskActivity.class).putExtra("task", current), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE));
        holder.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(mContext).setMessage(mContext.getText(R.string.prompt_delete_task)).setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
            MainActivity.mTaskViewModel.delete(current);
            Snackbar.make(holder.itemView, mContext.getString(R.string.snack_task_deleted), Snackbar.LENGTH_SHORT).show();
        }).setNegativeButton(mContext.getText(R.string.no), null).create().show());
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(mInflater.inflate(R.layout.task_list_item, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return mTasks.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return mTasks.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout expanded;
        private final ImageButton btnDelete, btnEdit, btnExpand;
        private final MaterialCardView parent;
        private final TextView txtDueDate, txtLabel, txtNotes, txtTime;
        private TaskViewHolder(View itemView) {
            super(itemView);
            // UI elements
            expanded = itemView.findViewById(R.id.expanded);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnExpand = itemView.findViewById(R.id.btnExpand);
            parent = itemView.findViewById(R.id.parent);
            txtNotes = itemView.findViewById(R.id.txtNotes);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
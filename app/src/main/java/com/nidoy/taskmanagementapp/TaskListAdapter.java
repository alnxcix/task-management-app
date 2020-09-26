package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    // Member variables
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat("MMM. dd (h:mm aa)");
    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<Task> mTasks; // Cached copy of tasks

    TaskListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        // Set the data to UI elements
        Task current = mTasks.get(position);
        holder.task_heading.setText(current.getmLabel());
        holder.task_subheading.setText(mContext.getResources().getString(R.string.due_on) + " " + formatter.format(current.getmDue()));
        holder.task_description.setText(current.getmDescription());
        holder.chipOpen.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.open)));
        holder.chipPending.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.pending)));
        holder.chipFinished.setEnabled(!current.getmStatus().equals(mContext.getString(R.string.finished)));

        // Set event listeners
        holder.btnExpand.setOnClickListener(v -> {
            holder.expandableLayout.setVisibility(holder.expandableLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            holder.btnExpand.setImageResource(holder.expandableLayout.getVisibility() == View.GONE ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_up_black_24dp);
        });
        holder.chipOpen.setOnClickListener(v -> {
            // create an alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_open_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.open));
                TasksFragment.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_open), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });
        holder.chipPending.setOnClickListener(v -> {
            // create an alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_pending_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.pending));
                TasksFragment.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_pending), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });
        holder.chipFinished.setOnClickListener(v -> {
            // create an alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_finished_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                current.setmStatus(mContext.getString(R.string.finished));
                TasksFragment.mTaskViewModel.update(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_finished), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });
        holder.chipDelete.setOnClickListener(v -> {
            // create an alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getText(R.string.prompt_delete_task));
            builder.setPositiveButton(mContext.getText(R.string.yes), (dialog, which) -> {
                TasksFragment.mTaskViewModel.delete(current);
                Toast.makeText(mContext, mContext.getText(R.string.toast_task_deleted), Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(mContext.getText(R.string.no), (dialog, which) -> {
            });
            builder.create().show();
        });
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        // Declare UI elements
        private final ConstraintLayout expandableLayout;
        private final ImageButton btnExpand;
        private final TextView task_heading, task_subheading, task_description;
        private final Chip chipOpen, chipPending, chipFinished, chipEdit, chipDelete;

        private TaskViewHolder(View itemView) {
            super(itemView);

            // Initialize UI elements
            btnExpand = itemView.findViewById(R.id.btnExpand);
            chipDelete = itemView.findViewById(R.id.chipDelete);
            chipEdit = itemView.findViewById(R.id.chipEdit);
            chipFinished = itemView.findViewById(R.id.chipFinished);
            chipOpen = itemView.findViewById(R.id.chipOpen);
            chipPending = itemView.findViewById(R.id.chipPending);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            task_description = itemView.findViewById(R.id.task_description);
            task_heading = itemView.findViewById(R.id.task_heading);
            task_subheading = itemView.findViewById(R.id.task_subheading);
        }
    }
}
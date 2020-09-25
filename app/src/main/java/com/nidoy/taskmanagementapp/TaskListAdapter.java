package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    // Member variables
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat("h:mm aa, MMMM dd, yyyy");
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Set the data to UI elements
        Task current = mTasks.get(position);
        holder.task_header.setText(current.getmLabel());
        holder.task_subheading.setText(mContext.getResources().getString(R.string.label_due_on) + " " + formatter.format(current.getmDue()));
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTasks != null) return mTasks.size();
        else return 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        // Declare UI elements
        private final TextView task_header;
        private final TextView task_subheading;
        private /* final */ TextView task_description;

        private TaskViewHolder(View itemView) {
            super(itemView);

            // Initialize UI elements
            task_header = itemView.findViewById(R.id.task_header);
            task_subheading = itemView.findViewById(R.id.task_subheading);
            // task_description = itemView.findViewById(R.id.task_description);
        }
    }
}
package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.txtTime.setText(new SimpleDateFormat("hh:mm aa").format(current.getmDue()));
        holder.monthStamp.setText(new SimpleDateFormat("MMM").format(current.getmDue()));
        holder.dateStamp.setText(new SimpleDateFormat("dd").format(current.getmDue()));

        try {
            holder.stamp.setVisibility(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(mTasks.get(position - 1).getmDue())) ? View.INVISIBLE : View.VISIBLE);
        } catch (Exception e) {
            holder.stamp.setVisibility(View.VISIBLE);
        }

        try {
            holder.divider.setVisibility(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(mTasks.get(position + 1).getmDue())) ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            holder.divider.setVisibility(View.GONE);
        }

        // Event listener(s)
        holder.btnShowMenu.setOnClickListener(v -> TasksBottomSheetFragment.newInstance(current).show(((FragmentActivity) mContext).getSupportFragmentManager(), "dialog"));
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
        private final ImageButton btnShowMenu;
        private final TextView txtLabel, txtTime, monthStamp, dateStamp;
        private final LinearLayout stamp;
        private final View divider;

        private TaskViewHolder(View itemView) {
            super(itemView);
            // UI elements
            btnShowMenu = itemView.findViewById(R.id.btnShowMenu);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtTime = itemView.findViewById(R.id.txtTime);
            monthStamp = itemView.findViewById(R.id.monthStamp);
            dateStamp = itemView.findViewById(R.id.dateStamp);
            stamp = itemView.findViewById(R.id.stamp);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
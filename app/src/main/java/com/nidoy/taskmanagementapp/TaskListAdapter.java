package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        holder.txtMonth.setText(new SimpleDateFormat("MMM").format(current.getmDue()));
        holder.txtDate.setText(new SimpleDateFormat("dd").format(current.getmDue()));

        if (new SimpleDateFormat("MMM. dd, yyyy").format(new Date()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()))) {
            holder.txtMonth.setTextColor(mContext.getResources().getColor(R.color.scarlet));
            holder.txtDate.setTextColor(mContext.getResources().getColor(R.color.scarlet));
        }

        try {
            holder.txtMonth.setVisibility(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(mTasks.get(position - 1).getmDue())) ? View.INVISIBLE : View.VISIBLE);
            holder.txtDate.setVisibility(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(mTasks.get(position - 1).getmDue())) ? View.INVISIBLE : View.VISIBLE);
        } catch (Exception e) {
            holder.txtMonth.setVisibility(View.VISIBLE);
            holder.txtDate.setVisibility(View.VISIBLE);
        }

        try {
            holder.divider.setVisibility(new SimpleDateFormat("MMM. dd, yyyy").format(current.getmDue()).equals(new SimpleDateFormat("MMM. dd, yyyy").format(mTasks.get(position + 1).getmDue())) ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            holder.divider.setVisibility(View.GONE);
        }

        holder.taskCard.setOnClickListener(v -> TasksBottomSheetFragment.newInstance(current).show(((FragmentActivity) mContext).getSupportFragmentManager(), "dialog"));
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
        private final TextView txtMonth, txtDate, txtLabel, txtTime;
        private final View divider;
        private final MaterialCardView taskCard;

        private TaskViewHolder(View itemView) {
            super(itemView);
            txtMonth = itemView.findViewById(R.id.txtMonth);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtTime = itemView.findViewById(R.id.txtTime);
            divider = itemView.findViewById(R.id.divider);
            taskCard = itemView.findViewById(R.id.taskCard);
        }
    }
}
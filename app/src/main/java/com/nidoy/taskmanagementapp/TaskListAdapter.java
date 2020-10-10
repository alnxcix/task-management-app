package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    List<Task> tasks; // Cached copy of tasks

    TaskListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = tasks.get(position);

        holder.txtLabel.setText(current.getLabel());
        holder.txtTime.setText(new SimpleDateFormat("hh:mm aa").format(current.getDue()));
        holder.txtMonth.setText(new SimpleDateFormat("MMM").format(current.getDue()));
        holder.txtDate.setText(new SimpleDateFormat("dd").format(current.getDue()));

        try {
            holder.txtMonth.setVisibility(new SimpleDateFormat("yyyyMMMdd").format(current.getDue()).equals(new SimpleDateFormat("yyyyMMMdd").format(tasks.get(position - 1).getDue())) ? View.INVISIBLE : View.VISIBLE);
            holder.txtDate.setVisibility(new SimpleDateFormat("yyyyMMMdd").format(current.getDue()).equals(new SimpleDateFormat("yyyyMMMdd").format(tasks.get(position - 1).getDue())) ? View.INVISIBLE : View.VISIBLE);
        } catch (Exception e) {
            holder.txtMonth.setVisibility(View.VISIBLE);
            holder.txtDate.setVisibility(View.VISIBLE);
        }

        if (new SimpleDateFormat("yyyyMMMdd").format(new Date()).equals(new SimpleDateFormat("yyyyMMMdd").format(current.getDue()))) {
            holder.txtMonth.setTextColor(context.getResources().getColor(R.color.scarlet));
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.scarlet));
        } else {
            holder.txtMonth.setTextColor(context.getResources().getColor(R.color.pine_dark));
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.pine_dark));
        }

        holder.txtLabel.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(current.getLegendColor(), PorterDuff.Mode.SRC_IN));

        try {
            holder.divider.setVisibility(new SimpleDateFormat("yyyyMMMdd").format(current.getDue()).equals(new SimpleDateFormat("yyyyMMMdd").format(tasks.get(position + 1).getDue())) ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            holder.divider.setVisibility(View.GONE);
        }

        holder.imgOverdue.setVisibility(current.getDue().getTime() <= new Date().getTime() && current.getStatus() != R.string.finished ? View.VISIBLE : View.INVISIBLE);

        holder.taskCard.setOnClickListener(v -> TasksBottomSheetFragment.newInstance(current).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(layoutInflater.inflate(R.layout.task_list_item, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtMonth, txtDate, txtLabel, txtTime;
        private final View divider;
        private final MaterialCardView taskCard;
        private final ImageView imgOverdue;

        private TaskViewHolder(View itemView) {
            super(itemView);
            txtMonth = itemView.findViewById(R.id.txtMonth);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtTime = itemView.findViewById(R.id.txtTime);
            divider = itemView.findViewById(R.id.divider);
            taskCard = itemView.findViewById(R.id.taskCard);
            imgOverdue = itemView.findViewById(R.id.imgOverdue);
        }
    }
}
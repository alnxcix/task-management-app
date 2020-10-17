package com.nidoy.taskmanagementapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<Schedule> schedules; // Cached copy of schedules

    ScheduleListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduleListAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(inflater.inflate(R.layout.schedule_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListAdapter.ScheduleViewHolder holder, int position) {
        Schedule current = schedules.get(position);

        holder.scheduleCard.setCardBackgroundColor(current.getThemeId());
        holder.imgFolder.setColorFilter(current.getThemeId() == context.getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
        holder.txtName.setText(current.getName());
        holder.txtName.setTextColor(current.getThemeId() == context.getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
        holder.scheduleCard.setOnClickListener(v -> SchedulesBottomSheetFragment.newInstance(current).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
    }

    @Override
    public long getItemId(int position) {
        return schedules.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return schedules != null ? schedules.size() : 0;
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView scheduleCard;
        private final ImageView imgFolder;
        private final TextView txtName;

        private ScheduleViewHolder(View itemView) {
            super(itemView);
            scheduleCard = itemView.findViewById(R.id.scheduleCard);
            imgFolder = itemView.findViewById(R.id.imgFolder);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}

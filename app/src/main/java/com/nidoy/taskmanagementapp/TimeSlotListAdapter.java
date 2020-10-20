package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.util.List;

public class TimeSlotListAdapter extends RecyclerView.Adapter<TimeSlotListAdapter.TimeSlotViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    List<TimeSlot> timeSlots;
    // Material pickers + builders
    private MaterialTimePicker materialTimePicker1;
    private MaterialTimePicker.Builder builder1;
    private MaterialTimePicker materialTimePicker2;
    private MaterialTimePicker.Builder builder2;

    TimeSlotListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeSlotListAdapter.TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeSlotListAdapter.TimeSlotViewHolder(layoutInflater.inflate(R.layout.timeslot_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimeSlotListAdapter.TimeSlotViewHolder holder, int position) {
        TimeSlot current = timeSlots.get(position);
        holder.txtDay.setText(context.getText(current.getDayId()));
        holder.editTextStartTime.setOnClickListener(v1 -> {
            builder1 = new MaterialTimePicker.Builder();
            builder1.setTimeFormat(TimeFormat.CLOCK_12H);
            builder1.setTitleText(context.getString(R.string.select_time));
            builder1.setHour(current.getStartTime().getHour());
            builder1.setMinute(current.getStartTime().getMinute());
            materialTimePicker1 = builder1.build();
            materialTimePicker1.addOnPositiveButtonClickListener(v2 -> {
                current.setStartTime(LocalTime.of(materialTimePicker1.getHour(), materialTimePicker1.getMinute()));
                holder.editTextStartTime.setText(materialTimePicker1.getHour() + ":" + materialTimePicker1.getMinute());
            });
            materialTimePicker1.show(((FragmentActivity) context).getSupportFragmentManager(), "TIME_PICKER");
        });
        holder.editTextEndTime.setOnClickListener(v1 -> {
            builder2 = new MaterialTimePicker.Builder();
            builder2.setTimeFormat(TimeFormat.CLOCK_12H);
            builder2.setTitleText("Select time");
            builder2.setHour(current.getEndTime().getHour());
            builder2.setMinute(current.getEndTime().getMinute());
            materialTimePicker2 = builder2.build();
            materialTimePicker2.addOnPositiveButtonClickListener(v2 -> {
                current.setEndTime(LocalTime.of(materialTimePicker2.getHour(), materialTimePicker2.getMinute()));
                holder.editTextEndTime.setText(materialTimePicker2.getHour() + ":" + materialTimePicker2.getMinute());
            });
            materialTimePicker2.show(((FragmentActivity) context).getSupportFragmentManager(), "TIME_PICKER");
        });
    }

    @Override
    public int getItemCount() {
        return timeSlots != null ? timeSlots.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return timeSlots.get(position).getId();
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtDay;
        private final EditText editTextStartTime, editTextEndTime;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            editTextStartTime = ((TextInputLayout) itemView.findViewById(R.id.txtInputStartTime)).getEditText();
            editTextEndTime = ((TextInputLayout) itemView.findViewById(R.id.txtInputEndTime)).getEditText();
        }
    }
}

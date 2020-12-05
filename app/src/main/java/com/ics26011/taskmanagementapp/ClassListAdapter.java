package com.ics26011.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.time.LocalTime;
import java.util.List;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    List<Class> classes; // Cached copy of classes

    ClassListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setClasses(List<Class> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassListAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassListAdapter.ClassViewHolder(layoutInflater.inflate(R.layout.class_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClassListAdapter.ClassViewHolder holder, int position) {
        Class c = classes.get(position);
        final LocalTime duration = LocalTime.ofNanoOfDay(c.getEndTime().toNanoOfDay() - c.getStartTime().toNanoOfDay());
        holder.chipTime.setText(c.getStartTime().toString());
        holder.chipTime.setChipBackgroundColor(ColorStateList.valueOf(ColorUtils.setAlphaComponent(c.getThemeId(), 64)));
        holder.imgMark.setColorFilter(c.getThemeId());
        holder.viewTop.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        holder.viewBottom.setVisibility(position == classes.size() - 1 ? View.GONE : View.VISIBLE);
        holder.cardClass.setRippleColor(ColorStateList.valueOf(c.getThemeId()));
        holder.txtTime.setText(c.getStartTime().toString() + " - " + c.getEndTime().toString());
        holder.txtHours.setText(duration.getHour() + " " + context.getString(R.string.hours) + ", " + duration.getMinute() + " " + context.getString(R.string.minutes));
        holder.txtName.setText(c.getName());
        holder.txtInstructors.setVisibility(c.getInstructors() == null ? View.GONE : View.VISIBLE);
        holder.txtInstructors.setText(c.getInstructors());
        holder.txtVenue.setVisibility(c.getVenue() == null ? View.GONE : View.VISIBLE);
        holder.txtVenue.setText(c.getVenue());
        // Event listeners
        holder.chipTime.setOnClickListener(v -> ClassesBottomSheetFragment.newInstance(c).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
        holder.cardClass.setOnClickListener(v -> ClassesBottomSheetFragment.newInstance(c).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
    }

    @Override
    public long getItemId(int position) {
        return classes.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return classes == null ? 0 : classes.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        final Chip chipTime;
        final ImageView imgMark;
        final View viewTop, viewBottom;
        final MaterialCardView cardClass;
        final TextView txtName, txtTime, txtHours, txtInstructors, txtVenue;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            chipTime = itemView.findViewById(R.id.chipTime);
            imgMark = itemView.findViewById(R.id.imgMark);
            viewTop = itemView.findViewById(R.id.viewTop);
            viewBottom = itemView.findViewById(R.id.viewBottom);
            cardClass = itemView.findViewById(R.id.cardClass);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtHours = itemView.findViewById(R.id.txtHours);
            txtName = itemView.findViewById(R.id.txtName);
            txtInstructors = itemView.findViewById(R.id.txtInstructors);
            txtVenue = itemView.findViewById(R.id.txtVenue);
        }
    }
}/**/
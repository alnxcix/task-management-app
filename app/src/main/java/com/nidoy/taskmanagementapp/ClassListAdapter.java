package com.nidoy.taskmanagementapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(@NonNull ClassListAdapter.ClassViewHolder holder, int position) {
        Class c = classes.get(position);
        holder.txtTime.setVisibility(View.GONE);
        holder.txtName.setText(c.getName());
        holder.txtName.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(c.getThemeId(), PorterDuff.Mode.SRC_IN));
        holder.txtInstructors.setText(c.getInstructors());
        holder.txtInstructors.setVisibility(c.getInstructors() == null ? View.GONE : View.VISIBLE);
        holder.txtVenue.setText(c.getVenue());
        holder.txtVenue.setVisibility(c.getVenue() == null ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return classes != null ? classes.size() : 0;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtTime, txtInstructors, txtVenue;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtName = itemView.findViewById(R.id.txtName);
            txtInstructors = itemView.findViewById(R.id.txtInstructors);
            txtVenue = itemView.findViewById(R.id.txtVenue);
        }
    }
}

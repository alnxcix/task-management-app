package com.ics26011.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class NotebookListAdapter extends RecyclerView.Adapter<NotebookListAdapter.NotebookViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    List<Notebook> notebooks; // Cached copy of notebooks

    NotebookListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotebookListAdapter.NotebookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotebookListAdapter.NotebookViewHolder(layoutInflater.inflate(R.layout.notebook_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotebookListAdapter.NotebookViewHolder holder, int position) {
        Notebook current = notebooks.get(position);
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{current.getThemeId(), ColorUtils.blendARGB(current.getThemeId(), Color.BLACK, 0.5f)});
        holder.viewTheme.setBackground(gd);
        holder.txtName.setText(current.getName());
        holder.txtNumNotes.setText(current.getNumNotes() + " " + context.getString(R.string.notes));
        holder.notebookCard.setOnClickListener(v -> NotebooksBottomSheetFragment.newInstance(current).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
        holder.notebookCard.setRippleColor(ColorStateList.valueOf(current.getThemeId()));
    }

    @Override
    public long getItemId(int position) {
        return notebooks.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return notebooks == null ? 0 : notebooks.size();
    }

    public static class NotebookViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView notebookCard;
        final View viewTheme;
        final TextView txtName, txtNumNotes;

        public NotebookViewHolder(@NonNull View itemView) {
            super(itemView);
            notebookCard = itemView.findViewById(R.id.notebookCard);
            viewTheme = itemView.findViewById(R.id.viewTheme);
            txtName = itemView.findViewById(R.id.txtName);
            txtNumNotes = itemView.findViewById(R.id.txtNumNotes);
        }
    }
}
package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    List<Note> notes; // Cached copy of notes

    NoteListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListAdapter.NoteViewHolder(layoutInflater.inflate(R.layout.note_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.txtLastModified.setText(current.getDateLastModified().format(DateTimeFormatter.ofPattern("MMM. dd, yy")));
        holder.txtTitle.setText(current.getTitle());
        holder.txtContent.setText(current.getContent());
        holder.noteCard.setBackgroundColor(ColorUtils.setAlphaComponent(current.getThemeId(), 101));
        holder.noteCard.setRippleColor(ColorStateList.valueOf(current.getThemeId()));
        holder.noteCard.setOnClickListener(v -> NotesBottomSheetFragment.newInstance(current).show(((FragmentActivity) context).getSupportFragmentManager(), "dialog"));
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView noteCard;
        final TextView txtLastModified, txtTitle, txtContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteCard = itemView.findViewById(R.id.noteCard);
            txtLastModified = itemView.findViewById(R.id.txtLastModified);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }
}

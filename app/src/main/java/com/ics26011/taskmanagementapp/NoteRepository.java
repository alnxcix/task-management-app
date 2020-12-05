package com.ics26011.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private final NoteDAO noteDAO;

    NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        noteDAO = db.noteDAO();
    }

    LiveData<List<Note>> getNotes(int notebookId) {
        return noteDAO.getNotes(notebookId);
    }

    void insert(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> noteDAO.insert(note));
    }

    void update(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> noteDAO.update(note));
    }

    void delete(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> noteDAO.delete(note));
    }
}

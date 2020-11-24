package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository noteRepository;

    public NoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    LiveData<List<Note>> getNotes(int notebookId) {
        return noteRepository.getNotes(notebookId);
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }
}

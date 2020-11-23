package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotebookViewModel extends AndroidViewModel {
    private final NotebookRepository notebookRepository;

    public NotebookViewModel(Application application) {
        super(application);
        notebookRepository = new NotebookRepository(application);
    }

    LiveData<List<Notebook>> getNotebooksSortedByName() {
        return notebookRepository.getNotebooksSortedByName();
    }

    public void insert(Notebook notebook) {
        notebookRepository.insert(notebook);
    }

    public void update(Notebook notebook) {
        notebookRepository.update(notebook);
    }

    public void delete(Notebook notebook) {
        notebookRepository.delete(notebook);
    }
}

package com.nidoy.taskmanagementapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotebookRepository {
    private final NotebookDAO notebookDAO;

    NotebookRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        notebookDAO = db.notebookDAO();
    }

    LiveData<List<Notebook>> getNotebooksSortedByName() {
        return notebookDAO.getNotebooksSortedByName();
    }

    void insert(Notebook notebook) {
        AppDatabase.databaseWriteExecutor.execute(() -> notebookDAO.insert(notebook));
    }

    void update(Notebook notebook) {
        AppDatabase.databaseWriteExecutor.execute(() -> notebookDAO.update(notebook));
    }

    void delete(Notebook notebook) {
        AppDatabase.databaseWriteExecutor.execute(() -> notebookDAO.delete(notebook));
    }
}
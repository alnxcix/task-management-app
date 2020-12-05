package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    void insert(Note note);

    @Query("SELECT * FROM note_table WHERE notebookId IS :notebookId ORDER BY dateLastModified")
    LiveData<List<Note>> getNotes(int notebookId);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
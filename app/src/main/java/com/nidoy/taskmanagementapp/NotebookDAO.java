package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotebookDAO {
    @Insert
    void insert(Notebook notebook);

    @Query("SELECT * FROM notebook_table ORDER BY name ASC")
    LiveData<List<Notebook>> getNotebooksSortedByName();

    @Update
    void update(Notebook notebook);

    @Delete
    void delete(Notebook notebook);
}

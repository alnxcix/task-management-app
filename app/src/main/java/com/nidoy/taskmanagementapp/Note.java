package com.nidoy.taskmanagementapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "note_table", foreignKeys = @ForeignKey(entity = Notebook.class, parentColumns = "id", childColumns = "notebookId", onDelete = ForeignKey.CASCADE))
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int notebookId, themeId;
    private LocalDateTime dateLastModified;
    private String content, title;

    public Note(int notebookId) {
        this.notebookId = notebookId;
        this.themeId = -1;
        this.dateLastModified = null;
        this.content = null;
        this.title = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(int notebookId) {
        this.notebookId = notebookId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

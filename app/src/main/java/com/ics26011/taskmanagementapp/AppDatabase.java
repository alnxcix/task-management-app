package com.ics26011.taskmanagementapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Notebook.class, Note.class, Schedule.class, Class.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db").addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }

    public abstract TaskDAO taskDAO();

    public abstract NotebookDAO notebookDAO();

    public abstract NoteDAO noteDAO();

    public abstract ScheduleDAO scheduleDAO();

    public abstract ClassDAO classDAO();
}
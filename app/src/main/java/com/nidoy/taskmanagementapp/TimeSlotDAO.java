package com.nidoy.taskmanagementapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeSlotDAO {

    @Insert
    void insert(TimeSlot timeSlot);

    @Query("SELECT * FROM timeSlot_table WHERE :classId LIKE classId")
    LiveData<List<TimeSlot>> getClassTimeSlots(int classId);

    @Update
    void update(TimeSlot timeSlot);

    @Delete
    void delete(TimeSlot timeSlot);
}

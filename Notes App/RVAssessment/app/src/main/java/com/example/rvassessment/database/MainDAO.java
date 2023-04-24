package com.example.rvassessment.database;

import static androidx.room.OnConflictStrategy.REPLACE;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rvassessment.Notes.NoteModel;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(NoteModel notes);

    @Query("SELECT * FROM notes ORDER BY priority DESC,  title ASC")
    List<NoteModel> getAll();

    @Query("UPDATE notes SET title = :title, description = :description, priority = :priority WHERE ID = :id")
    void update (int id, String title, String description, boolean priority);

    @Delete
    void delete(NoteModel notes);

    @Query("UPDATE notes SET priority = :priority WHERE ID = :id")
    void setPriority(int id, boolean priority);

    @Update
    void update(NoteModel note);
}

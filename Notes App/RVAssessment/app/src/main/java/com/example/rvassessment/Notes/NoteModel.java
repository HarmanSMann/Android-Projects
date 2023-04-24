package com.example.rvassessment.Notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName =  "notes")
public class NoteModel implements Serializable{

    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    @ColumnInfo(name = "title")
    String title = "";
    @ColumnInfo(name = "description")
    String description = "";
    @ColumnInfo(name = "date")
    String date = "";
    @ColumnInfo(name = "priority")
    public
    boolean priority;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getPriority() {return this.priority; }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

}
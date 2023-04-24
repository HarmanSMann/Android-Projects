package com.example.rvassessment.Notes;


import androidx.cardview.widget.CardView;

public interface NotesClickListener {
    void onClick(NoteModel notes);
    void onLongClick(NoteModel notes, CardView cardView);
}

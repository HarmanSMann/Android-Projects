package com.example.rvassessment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rvassessment.Notes.NoteModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotePad2 extends AppCompatActivity {

    Button saveNote;
    EditText newNoteTitle, newNoteDescription;
    CheckBox newNotePriority;
    NoteModel notes;
    boolean oldNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad);
        saveNote = findViewById(R.id.saveNote);
        newNoteTitle = findViewById(R.id.newNoteTitle);
        newNoteDescription = findViewById(R.id.newNoteDescription);
        newNotePriority = findViewById(R.id.newNotePriority);

        try {
            notes = (NoteModel) getIntent().getSerializableExtra("oldNote");
            newNoteTitle.setText(notes.getTitle());
            newNoteDescription.setText(notes.getDescription());
            if (notes.getPriority()) {
                newNotePriority.setChecked(true);
            }else {
                newNotePriority.setChecked(false);
            }
            oldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = newNoteTitle.getText().toString();
                String description = newNoteDescription.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(NotePad2.this, "Please add a title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.isEmpty()) {
                    Toast.makeText(NotePad2.this, "Please add a description", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MM dd, yyyy");
                Date date = new Date();

                if (!oldNote) {
                    notes = new NoteModel();
                }
                notes.setTitle(title);
                notes.setDescription(description);
                notes.setDate(formatter.format(date));

                if (newNotePriority.isChecked()) {
                    notes.setPriority(true);
                    newNotePriority.setChecked(true);
                } else {
                    notes.setPriority(false);
                    newNotePriority.setChecked(false);
                }

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
package com.example.rvassessment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.MenuItem;
import android.widget.PopupMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.rvassessment.Notes.NoteModel;
import com.example.rvassessment.Notes.NotesClickListener;
import com.example.rvassessment.database.MainDAO;
import com.example.rvassessment.database.RoomDB;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button newNote;
    NotesAdaptor adaptor;
    MainDAO mainDAO;

    List<NoteModel> notes = new ArrayList<>();
    RoomDB database;
    NoteModel selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerNotebook);
        newNote = findViewById(R.id.newNote);
        mainDAO = RoomDB.getAppDatabase(this).mainDAO();
        database = RoomDB.getInstance(MainActivity.this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        newNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotePad2.class);
            startActivityForResult(intent, 101);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                NoteModel new_notes = (NoteModel) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
            }
        }else if (requestCode == 102) {
            if (resultCode==Activity.RESULT_OK){
                NoteModel new_notes = (NoteModel) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getDescription(), new_notes.priority);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
            }
        }
        updateRecycler(notes);
    }

    private void updateRecycler(List<NoteModel> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adaptor = new NotesAdaptor(MainActivity.this, notes, notesClickListener, mainDAO);
        recyclerView.setAdapter(adaptor);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(NoteModel notes) {
            Intent intent = new Intent(MainActivity.this, NotePad2.class);
            intent.putExtra("oldNote", notes);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(NoteModel notes, CardView cardView) {
            selectedNote = new NoteModel();
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, cardView);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.delete) {
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notes = mainDAO.getAll();
                //adaptor.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
                updateRecycler(notes);
            }
            return true;
        });
        popupMenu.show();
    }
}
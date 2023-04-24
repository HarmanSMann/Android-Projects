package com.example.rvassessment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rvassessment.Notes.NoteModel;
import com.example.rvassessment.Notes.NotesClickListener;
import com.example.rvassessment.database.MainDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.ViewHolder> {
    Context context;
    List<NoteModel> notebook;
    NotesClickListener listener;
    MainDAO mainDAO;


    public NotesAdaptor(Context context, List<NoteModel> list, NotesClickListener listener, MainDAO mainDAO) {
        this.context = context;
        this.notebook = list;
        this.listener = listener;
        this.mainDAO = mainDAO;
    }

    @Override
    public NotesAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.noteblock, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NoteModel note = notebook.get(position);
        holder.title.setText(notebook.get(position).getTitle());
        //holder.title.setSelected(true);
        holder.description.setText(notebook.get(position).getDescription());
        SimpleDateFormat format = new SimpleDateFormat("MM dd, yyyy");
        holder.date.setText(format.format(new Date()));
        holder.priority.setChecked(notebook.get(position).priority);


        holder.getPriority().setOnClickListener(view -> {
            note.setPriority(holder.getPriority().isChecked());
            note.setDate(String.valueOf(new Date()));
            mainDAO.update(note);
            notebook = mainDAO.getAll();
            notifyDataSetChanged();
            String text;
            if (note.getPriority()) {
                text = "high";
            } else {
                text = "low";
            }
            Toast.makeText(view.getContext(),
                    notebook.get(position).getTitle() + " is " + text + " priority",
                    Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(view -> listener.onClick(notebook.get(holder.getAdapterPosition())));

        holder.notes_container.setOnLongClickListener(view -> {
            listener.onLongClick(notebook.get(holder.getAdapterPosition()), holder.notes_container);
            return true;
        });
    }
    @Override
    public int getItemCount() {
        return notebook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView date;
        private CheckBox priority;
        private CardView notes_container;

        public ViewHolder(@NonNull View view) {
            super(view);
            notes_container = view.findViewById(R.id.notes_container);
            title = view.findViewById(R.id.noteTitle);
            description = view.findViewById(R.id.description);
            date = view.findViewById(R.id.date);
            priority = view.findViewById(R.id.priority);
        }

        public CheckBox getPriority() {
            return priority;
        }

    }




}

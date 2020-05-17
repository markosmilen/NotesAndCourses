package com.marko.notesandcourses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter mNoteRecyclerAdapter;

    //private ArrayAdapter<NoteInfo> mAdapterNotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        final List<NoteInfo> notes = DataManager.getInstance().getNotes();

        final RecyclerView recyclerNotes = findViewById(R.id.list_notes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        recyclerNotes.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
       // mAdapterNotes.notifyDataSetChanged();
    }
}

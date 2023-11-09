package com.dinsaren.androidsqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinsaren.androidsqlitedatabase.adapter.NoteAdapter;
import com.dinsaren.androidsqlitedatabase.app.BaseActivity;
import com.dinsaren.androidsqlitedatabase.database.DatabaseHelper;
import com.dinsaren.androidsqlitedatabase.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends BaseActivity {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewNote;
    private FloatingActionButton btnAdd;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        initView();
        getAllData();
    }

    private void initView() {
        recyclerViewNote = findViewById(R.id.recyclerViewNote);
        btnAdd = findViewById(R.id.btnAddNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    private void getAllData() {
        if (databaseHelper.getAll().isEmpty()) {
            databaseHelper.create(new Note(1, "My Note", "I love bbu"));
        }

        noteAdapter = new NoteAdapter(this, databaseHelper.getAll(), new NoteAdapter.OnClickListener() {
            @Override
            public void onClickItem(View view, Object object) {
                Note note = (Note) object;
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                intent.putExtra("ID", note.getId());
                intent.putExtra("TITLE", note.getTitle());
                intent.putExtra("DESCRIPTION", note.getDescription());
                startActivityForResult(intent, 1000);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerViewNote.setLayoutManager(gridLayoutManager);
        recyclerViewNote.setAdapter(noteAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            getAllData();
        }
    }
}
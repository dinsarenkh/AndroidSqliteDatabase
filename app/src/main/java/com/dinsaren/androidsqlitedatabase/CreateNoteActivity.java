package com.dinsaren.androidsqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dinsaren.androidsqlitedatabase.app.BaseActivity;
import com.dinsaren.androidsqlitedatabase.database.DatabaseHelper;
import com.dinsaren.androidsqlitedatabase.models.Note;

public class CreateNoteActivity extends BaseActivity {
    private TextView tvBack, txtTitle;
    private EditText title, description;
    private Button btnCreate;
    private Note note;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initView();
        Intent intent = getIntent();
        note.setId(intent.getIntExtra("ID", 0));
        note.setTitle(intent.getStringExtra("TITLE"));
        note.setDescription(intent.getStringExtra("DESCRIPTION"));
        if (note.getId() != 0) {
            btnCreate.setText("Update");
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            txtTitle.setText("Update Note");
        }
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateAndUpdate();
            }
        });
    }

    private void initView() {
        databaseHelper = new DatabaseHelper(this);
        note = new Note();
        tvBack = findViewById(R.id.txtBack);
        txtTitle = findViewById(R.id.txtTitle);
        title = findViewById(R.id.edTitle);
        description = findViewById(R.id.edDescription);
        btnCreate = findViewById(R.id.btnCreate);
    }

    public void onCreateAndUpdate() {
        if (title.getText().toString().equals("")) {
            showMessageSuccess("Please enter title");
            return;
        }
        if (description.getText().toString().equals("")) {
            showMessageSuccess("Please enter description");
            return;
        }
        note.setTitle(title.getText().toString().trim());
        note.setDescription(description.getText().toString().trim());
        if (note.getId() == 0) {
            databaseHelper.create(note);
        } else {
            databaseHelper.update(note);
        }
        Intent intent = new Intent();
        intent.putExtra("ID", note.getId());
        intent.putExtra("TITLE", note.getTitle());
        intent.putExtra("DESCRIPTION", note.getDescription());
        setResult(RESULT_OK, intent);
        finish();
    }
}
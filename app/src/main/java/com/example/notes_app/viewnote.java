package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class viewnote extends AppCompatActivity {

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnote);

        Button backBtn = findViewById(R.id.backBtn2);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        LinearLayout viewll = findViewById(R.id.viewScroll);

        fileName = getIntent().getStringExtra("filename");
        displayNoteContent(viewll);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBackToMain();
            }
        });
    }

    private void displayNoteContent(LinearLayout viewScrollLayout) {
        FileInputStream myFileInput = null;
        try {
            myFileInput = openFileInput(fileName);
            BufferedReader readData = new BufferedReader(new InputStreamReader(myFileInput));
            String noteContent = readData.readLine();
            TextView noteTextView = new TextView(this);
            noteTextView.setText(noteContent);
            noteTextView.setPadding(20, 20, 20, 20);
            viewScrollLayout.addView(noteTextView);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteNote() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getFilesDir();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().equals(fileName)) {
                file.delete();
                break;
            }
        }
        navigateBackToMain();
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}

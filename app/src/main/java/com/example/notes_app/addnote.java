package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class addnote extends AppCompatActivity {

    private EditText messageText;
    private Button back, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        // initialize views
        messageText = findViewById(R.id.editingText);
        back = findViewById(R.id.backBtn);
        save = findViewById(R.id.saveBtn);

        // handle save button click
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the text from the message text field
                String text = messageText.getText().toString().trim();

                // check if the text is not empty
                if (text.isEmpty()) {
                    Toast.makeText(addnote.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create a new file and write the text to it
                String fileName = "note_" + System.currentTimeMillis() + ".txt";
                File file = new File(getFilesDir(), fileName);
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    outputStream.write(text.getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // navigate back to the main activity
                Intent intent = new Intent(addnote.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // handle back button click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // navigate back to the main activity
                Intent intent = new Intent(addnote.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

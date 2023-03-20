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
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = findViewById(R.id.notes_layout);
        Button addanoteBtn = findViewById(R.id.addanoteBtn);

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getFilesDir();
        File[] files = directory.listFiles();

        int i = 0;
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                try {
                    FileInputStream myFileInput = openFileInput(fileName);
                    BufferedReader readData = new BufferedReader(new InputStreamReader(myFileInput));
                    String myTextFile = readData.readLine();
                    myFileInput.close();
                    readData.close();
                    if (myTextFile.length() > 25) {
                        myTextFile = myTextFile.substring(0, 25) + ".....";
                    }
                    TextView text = new TextView(this);
                    text.setText((i + 1) + " : " + myTextFile);
                    text.setPadding(25, 25, 25, 25);
                    ll.addView(text);
                    final String last = fileName;
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), viewnote.class);
                            intent.putExtra("filename", last);
                            startActivity(intent);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
        addanoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addnote.class);
                startActivity(intent);
            }
        });
    }
}

package com.alicearnautova;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ShowPresentationActivity extends AppCompatActivity {

    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentations_show_activity);

        Intent i = getIntent();
        selected = i.getStringExtra("PresentationTitle");

        getSupportActionBar().setTitle(selected);
    }
}

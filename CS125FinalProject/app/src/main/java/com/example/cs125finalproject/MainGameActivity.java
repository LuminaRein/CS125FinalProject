package com.example.cs125finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        Button returnButton = findViewById(R.id.return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(final View v) {

                returnToMenu();

            }

        });
    }

    private void returnToMenu() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();

    }
}

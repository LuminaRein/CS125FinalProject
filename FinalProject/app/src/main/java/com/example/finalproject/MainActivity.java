package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        configureButtons();

    }

    private void finishGame() {

        finish();

    }

    private void configureButtons() {

        Button startGame = (Button) findViewById(R.id.start_game_button);
        Button quitGame = (Button) findViewById(R.id.quit_button);

        Log.d("??", startGame.toString());

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {

                startActivity(new Intent(MainActivity.this, MainGameActivity.class));

                finish();

            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {

                finishGame();

            }
        });

    }
}

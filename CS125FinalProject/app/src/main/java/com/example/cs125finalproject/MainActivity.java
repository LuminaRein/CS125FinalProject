package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cs125finalproject.databinding.ActivityMainGameBinding;
import com.example.cs125finalproject.databinding.ActivityMainMenuBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);
        configureButtons();
    }

    private void finishGame() {

        finish();

    }

    private void configureButtons() {

        Button startGame = binding.startGameButton;
        Button quitGame = binding.quitButton;

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

package com.example.cs125finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cs125finalproject.databinding.ActivityMainGameBinding;
import com.example.cs125finalproject.databinding.ActivityMainMenuBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;
    private final String HIGH_SCORE = "highScore";
    private final String TRYS = "trys";

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
        Button credits = binding.creditsButton;

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

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {

                credits();

            }
        });

    }

    private void credits() {
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Credits~");
        String message = "";
        message += " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n" +
                "|  Team: Joe Luo\t(luo42)\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  \t\t\t\t\t\t\tRafael Velazquez\t(rafaelv3)\t\t\t\t\t\t\t|\n" +
                "|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Game Design: \t\t\tJoe\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  UI/Look Design: \t\tJoe\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Menu Design: \t\t\tRafael\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Score Design: \t\t\tRafael\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Software Library:\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|      Android Data Binding Library\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Game Coding: \t\t\tJoe\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|  Menu Coding: \t\t\tRafael\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n";
        if (prefs.getInt(HIGH_SCORE, 0) == 15000) {
            Integer trys = prefs.getInt(TRYS, 1);
            message += "|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n";
            message += "|  Trys You Took to Get The First 15000: " + trys.toString() + "\t\t|\n";
            message += "|  Yeah We Recorded That!! Gotcha!\t\t\t\t\t\t\t\t|\n";
        }
        message += "|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\n" +
                "|\t\t\t\t\t\t\tThanks for playing my game!\t\t\t\t\t\t\t|\n" +
                " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n";

        builder.setMessage(message);
        builder.show();


    }
}

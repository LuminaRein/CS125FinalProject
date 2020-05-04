package com.example.cs125finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.cs125finalproject.Logics.GameButton;

import java.util.Random;

public class MainGameActivity extends AppCompatActivity {

    private final int GAME_BUTTON_WIDTH = 72;
    private final int GAME_BUTTON_HEIGHT = 72;

    private GameButton[] gameButtons = new GameButton[9];

    /*
    private final int[] GAME_BUTTON_MARGIN_LEFT = {51, 162, 274, 51, 162, 274, 51, 162, 274};
    private final int[] GAME_BUTTON_MARGIN_TOP = {360, 360, 360, 478, 478, 478, 598, 598, 598};
    private final int[] GAME_BUTTON_MARGIN_RIGHT = {288, 178, 65, 288, 178, 65, 288, 178, 65};
    private final int[] GAME_BUTTON_MARGIN_BOTTOM = {300, 300, 300, 183, 183, 183, 62, 62, 62};
    */
    private final int BUTTON_MIDDLE = 0;
    private final int BUTTON_LEFT = 1;
    private final int BUTTON_RIGHT = 2;
    private final int BUTTON_CIRCLE = 3;
    private final int GAME_ON_GOING = 0;
    private int gameState = GAME_ON_GOING;
    private final int GAME_END = 1;

    private final int CIRCLE_SOCRE = 100;
    private final int LEFT_SCORE = 800;
    private final int RIGHT_SCORE = 1000;
    private final int MIDDLE_SCORE = 500;

    private final String HIGH_SCORE = "highScore";

    private int score;

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

        initializeGameButtons();

        updateGame();

    }

    private void gameEnd(int[] stats) {
        gameState = GAME_END;

        for (int i = 0; i < 4; i++) {
            //ALL MATCH
            if (stats[i] == 9) {

                score += 1000;

            }

        }
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Editor editor = prefs.edit();

        int highScore = 0;
        if (prefs.contains(HIGH_SCORE)) {

            highScore = prefs.getInt(HIGH_SCORE, 0);
            //Beaten High Score
            if (highScore < score) {
                highScore = score;
                editor.putInt(HIGH_SCORE, highScore);
            }

        } else {

            editor.putInt(HIGH_SCORE, score);

        }
        editor.commit();
    }

    private void updateGame() {

        if (gameState == GAME_END) {
            return;
        }
        int[] stats = new int[4];
        int[] positions = new int[9];
        for (int i = 0; i < 9; i++) {
            int state = gameButtons[i].getState();
            positions[i] = state;
            switch(state) {
                case BUTTON_CIRCLE:
                    stats[BUTTON_CIRCLE]++;
                    break;
                case BUTTON_MIDDLE:
                    stats[BUTTON_MIDDLE]++;
                    break;
                case BUTTON_LEFT:
                    stats[BUTTON_LEFT]++;
                    break;
                case BUTTON_RIGHT:
                    stats[BUTTON_RIGHT]++;
                    break;
            }

        }

        Integer estimatedScore = 0;
        estimatedScore = stats[BUTTON_MIDDLE] * MIDDLE_SCORE + stats[BUTTON_RIGHT] * RIGHT_SCORE
                + stats[BUTTON_LEFT] * LEFT_SCORE + stats[BUTTON_CIRCLE] * CIRCLE_SOCRE;
        score = estimatedScore;
        EditText textView = (EditText) findViewById(R.id.scoreLabel);
        textView.setText("Score: " + estimatedScore.toString());

        //Count for Game End Condition
        for (int i = 0; i < 4; i++) {
            if (stats[BUTTON_CIRCLE] + stats[i] == 9) {
                gameEnd(stats);
                return;
            }
        }

    }

    private void updateButton(GameButton button) {

        if(gameState == GAME_END) {

            return;

        }

        if (button.getState() == button.BUTTON_STATE_CIRCLE) {
            return;
        }

        int curState = button.updateState();

        switch(curState) {
            case BUTTON_MIDDLE:
                button.button.setImageResource(R.drawable.triangle_middle);
                break;
            case BUTTON_LEFT:
                button.button.setImageResource(R.drawable.triangle_right_left);
                break;
            case BUTTON_RIGHT:
                button.button.setImageResource(R.drawable.triangle_right_down);
                break;
            case BUTTON_CIRCLE:
                button.button.setImageResource(R.drawable.circle);
                break;
        }

        updateGame();
    }

    private void initializeGameButtons() {

        Random random = new Random();

        int middleNums = 4 + random.nextInt(2);
        int leftNums = 2 + random.nextInt(2);
        int rightNums = 9 - middleNums - leftNums;

        int[] buttonAvailable = new int[3];
        buttonAvailable[0] = middleNums;
        buttonAvailable[1] = leftNums;
        buttonAvailable[2] = rightNums;

        int[] buttonPosition = new int[9];

        for (int i = 0; i < 9; i++) {
            int choice = random.nextInt(3);
            while (buttonAvailable[choice] <= 0) {
                choice = random.nextInt(3);
            }
            buttonPosition[i] = choice;

            buttonAvailable[choice]--;
        }

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        ImageButton button0 = (ImageButton) findViewById(R.id.button0);
        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        ImageButton button5 = (ImageButton) findViewById(R.id.button5);
        ImageButton button6 = (ImageButton) findViewById(R.id.button6);
        ImageButton button7 = (ImageButton) findViewById(R.id.button7);
        ImageButton button8 = (ImageButton) findViewById(R.id.button8);
        gameButtons[0] = new GameButton(button0, buttonPosition[0]);
        gameButtons[0].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[0]);
            }
        });
        gameButtons[1] = new GameButton(button1, buttonPosition[1]);
        gameButtons[1].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[1]);
            }
        });
        gameButtons[2] = new GameButton(button2, buttonPosition[2]);
        gameButtons[2].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[2]);
            }
        });
        gameButtons[3] = new GameButton(button3, buttonPosition[3]);
        gameButtons[3].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[3]);
            }
        });
        gameButtons[4] = new GameButton(button4, buttonPosition[4]);
        gameButtons[4].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[4]);
            }
        });
        gameButtons[5] = new GameButton(button5, buttonPosition[5]);
        gameButtons[5].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[5]);
            }
        });
        gameButtons[6] = new GameButton(button6, buttonPosition[6]);
        gameButtons[6].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[6]);
            }
        });
        gameButtons[7] = new GameButton(button7, buttonPosition[7]);
        gameButtons[7].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[7]);
            }
        });
        gameButtons[8] = new GameButton(button8, buttonPosition[8]);
        gameButtons[8].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[8]);
            }
        });

        for (int i = 0; i < 9; i++) {
            switch(buttonPosition[i]) {
                case BUTTON_MIDDLE:
                    gameButtons[i].button.setImageResource(R.drawable.triangle_middle);
                    break;
                case BUTTON_LEFT:
                    gameButtons[i].button.setImageResource(R.drawable.triangle_right_left);
                    break;
                case BUTTON_RIGHT:
                    gameButtons[i].button.setImageResource(R.drawable.triangle_right_down);
                    break;
            }
        }

    }

    private void returnToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

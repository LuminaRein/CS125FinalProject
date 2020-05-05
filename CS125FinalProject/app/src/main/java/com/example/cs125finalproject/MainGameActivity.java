package com.example.cs125finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import com.example.cs125finalproject.Logics.GameButton;
import com.example.cs125finalproject.databinding.ActivityMainGameBinding;
import com.example.cs125finalproject.databinding.ActivityMainGameBindingImpl;
import com.example.cs125finalproject.databinding.ActivityMainMenuBinding;

import java.util.Random;

public class MainGameActivity extends AppCompatActivity {

    private final int GAME_BUTTON_WIDTH = 72;
    private final int GAME_BUTTON_HEIGHT = 72;

    private GameButton[] gameButtons = new GameButton[9];

    ActivityMainGameBinding binding;

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
    private final String TRYS = "trys";

    private Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_game);
        //Initialize Buttons
        Button returnButton = binding.returnButton;
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(final View v) {
                returnToMenu();
            }

        });

        Button restartButton = binding.restartButton;
        restartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(final View v) {
                beginAnew();
            }

        });

        Button rules = binding.ruleButton;
        rules.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(final View v) {
                buildRulesAlertDialog();
            }

        });
        initializeGameButtons();

        updateGame();

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        binding.highScoreTextView.setText("High Score: " + prefs.getInt(HIGH_SCORE, 0));
    }

    private void buildRulesAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RULES: ");
        String message = "-------------------------------------\n";
        message += "Welcome to Some Kind Of Matching Game! The rules are simple here. Get all tiles" +
                " matched up (or screw up) and you win!\n" +
                "Well let's get to the specifics:\n" +
                "****************************\n" +
                "(1) There are four kinds of buttons, as I would like to address them:\n" +
                " \tI. Triangle_In_The_Middle Button, or Middle Button\n" +
                " \tII. Triangle_In_The_Right Button, or Right Button\n" +
                " \tIII. Triangle_In_The_Left Button, or Left Button\n" +
                " \tIV. Circle_of_The_End Button, or Circle Button\n\n" +
                "(2) Here are their functions. Except for unchangeable Circle Button, they all" +
                " follow a specific routine or pattern when clicked disregarding their middle states: \n" +
                "\tI. Middle Button: Will become Right/Left Button randomly, then Circle Button upon clicking\n" +
                "\tII. Right Button: Will become Middle Button, then Left Button, then Circle Button upon clicking\n" +
                "\tIII. Left Button: Will become Middle Button, then Right Button, then Circle Button upon clicking\n" +
                "\tIV. Circle Button: Will NOT change no matter what.\n\n" +
                "(3) The game will end when All tiles on the screen match or there is only one specific type of " +
                "tile left together with circle tiles. The scores each of them earns are:\n" +
                "\tI. Middle Button: 500 pts\n" +
                "\tII. Right Button: 1000 pts\n" +
                "\tIII. Left Button: 800 pts\n" +
                "\tIV. Circle Button: 100 pts\n" +
                "You will also be awarded 6000 pts whenever all tiles on the screen match, including Circle Buttons\n\n"+
                "Therefore, the theoretical maximum points is 15000 pts. It requires both a bit of luck and a bit of master" +
                "mind. If my calculations are correct, since the number of Middle Buttons are usually around four to five, the chance of getting" +
                "the highest score with the right technique is around 50% * 50% * 50% * 50% = 6.25% to 50 * 50% * 50% * 50% * 50%.= 3.125%. You " +
                "must be REALLY LUCKY to get it! Screenshot it to your friends and ruin their days!\n" +
                "****************************\n" +
                "And it is all up to you now!";


        builder.setMessage(message);
        builder.create().show();
    }

    private void beginAnew() {
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
        finish();
    }

    private void gameEnd(int[] stats) {
        gameState = GAME_END;

        for (int i = 0; i < 4; i++) {
            //ALL MATCH
            if (stats[i] == 9) {

                score += 6000;

            }

        }
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Editor editor = prefs.edit();

        //Easter Egg
        if (prefs.getInt(HIGH_SCORE, 0) != 15000) {
            if (prefs.contains(TRYS)) {
                editor.putInt(TRYS, prefs.getInt(TRYS, 0)+1);
            } else {
                editor.putInt(TRYS, 1);
            }
        }

        //Build Game Over Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game over!");
        builder.setIcon(R.drawable.diamond);
        String message;
        message  = "        Your score is: " + score.toString() + "\n";

        binding.scoreLabel.setText("Final Score: " + score.toString());

        Integer highScore = 0;
        if (prefs.contains(HIGH_SCORE)) {

            highScore = prefs.getInt(HIGH_SCORE, 0);
            message += "        Your High Score was: " + highScore.toString() + "\n";
            //Beaten High Score
            if (highScore < score) {
                highScore = score;
                editor.putInt(HIGH_SCORE, highScore);
                message += "        You've beaten the best of you in the past!\n";
                binding.highScoreTextView.setText("High Score: " + highScore);
            } else {
                message += "        The you in the past is superior it would seem!\n";
            }

            if (highScore == 15000 || score == 15000) {
                message += "        You have achieved the highest score possible!\n" +
                        "       See the credits in the main menu for easter eggs!\n";
            }

        } else {
            editor.putInt(HIGH_SCORE, score);
            message += "            Your new High Score is: " + highScore.toString() + "!\n";
            binding.highScoreTextView.setText("High Score: " + highScore);
        }
        editor.commit();
        builder.setMessage(message);
        builder.create().show();
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
        EditText textView = binding.scoreLabel;
        textView.setText("Estimated Score: " + estimatedScore.toString());

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

        RelativeLayout layout = binding.relativeLayout;
        /*
        ImageButton button0 = (ImageButton) findViewById(R.id.button0);
        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        ImageButton button5 = (ImageButton) findViewById(R.id.button5);
        ImageButton button6 = (ImageButton) findViewById(R.id.button6);
        ImageButton button7 = (ImageButton) findViewById(R.id.button7);
        ImageButton button8 = (ImageButton) findViewById(R.id.button8);
        */
        gameButtons[0] = new GameButton(binding.button0, buttonPosition[0]);
        gameButtons[0].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[0]);
            }
        });
        gameButtons[1] = new GameButton(binding.button1, buttonPosition[1]);
        gameButtons[1].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[1]);
            }
        });
        gameButtons[2] = new GameButton(binding.button2, buttonPosition[2]);
        gameButtons[2].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[2]);
            }
        });
        gameButtons[3] = new GameButton(binding.button3, buttonPosition[3]);
        gameButtons[3].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[3]);
            }
        });
        gameButtons[4] = new GameButton(binding.button4, buttonPosition[4]);
        gameButtons[4].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[4]);
            }
        });
        gameButtons[5] = new GameButton(binding.button5, buttonPosition[5]);
        gameButtons[5].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[5]);
            }
        });
        gameButtons[6] = new GameButton(binding.button6, buttonPosition[6]);
        gameButtons[6].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[6]);
            }
        });
        gameButtons[7] = new GameButton(binding.button7, buttonPosition[7]);
        gameButtons[7].button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(final View v) {
                updateButton(gameButtons[7]);
            }
        });
        gameButtons[8] = new GameButton(binding.button8, buttonPosition[8]);
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

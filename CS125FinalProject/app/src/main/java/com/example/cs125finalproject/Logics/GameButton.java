package com.example.cs125finalproject.Logics;

import android.app.ActionBar;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.Random;

class State
{
    int state;
    State next;

    public State(int buttonState, boolean canChange)
    {
        state = buttonState;

        if (canChange) {

            switch (state) {
                case 0:
                    Random rand = new Random();
                    int n = 1 + rand.nextInt(2);
                    int f = 3;
                    next = new State(n, false);
                    next.next = new State(f, false);
                    break;
                case 1:
                    n = 0;
                    int n1 = 2;
                    f = 3;
                    next = new State(n, false);
                    next.next = new State(n1, false);
                    next.next.next = new State(f, false);
                    break;
                case 2:
                    n = 0;
                    n1 = 1;
                    f = 3;
                    next = new State(n, false);
                    next.next = new State(n1, false);
                    next.next.next = new State(f, false);
                    break;
            }

        }
    }
}
public class GameButton {

    /*
    public int layout_width;
    public int layout_height;

    public int layout_margin_top;
    public int layout_margin_left;
    public int layout_margin_right;
    public int layout_margin_bottom;
    */

    public int currentState;
    public final int BUTTON_STATE_TRIANGLE_MIDDLE = 0;
    public final int BUTTON_STATE_TRIANGLE_LEFT = 1;
    public final int BUTTON_STATE_TRIANGLE_RIGHT = 2;
    public final int BUTTON_STATE_CIRCLE = 3;

    public State state;

    public ImageButton button;

    public GameButton(ImageButton b, int type) {

        button = b;

        currentState = type;

        state = new State(type, true);

    }

    public int getState() {

        return state.state;

    }

    public int updateState() {
        if (state.state == BUTTON_STATE_CIRCLE) {

            return BUTTON_STATE_CIRCLE;

        }
        state = state.next;

        return state.state;
    }

}

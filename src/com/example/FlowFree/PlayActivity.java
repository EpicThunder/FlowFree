package com.example.FlowFree;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kristján on 14.9.2014.
 */
public class PlayActivity extends Activity {

    private Global mGlobals = Global.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.play);
        TextView textView = (TextView)findViewById(R.id.puzzleNumber);
        textView.setText("Puzzle "+(mGlobals.puzzles.indexOf(mGlobals.puzzle)+1));

        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int padding = size.x/10;
        int heightPadding = size.y/10;
        findViewById(android.R.id.content).setPadding(padding,heightPadding,padding,0);
        findViewById(android.R.id.content).invalidate();*/
        Board board = (Board)findViewById(R.id.board);
        board.setPuzzle(mGlobals.puzzle);
    }
}
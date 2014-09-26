package com.example.FlowFree;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * Created by Kristján on 14.9.2014.
 */
public class PuzzleSelect extends Activity {

    private Global mGlobals = Global.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        GridLayout grid = (GridLayout)findViewById(R.id.grid);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int px, px2 = (int)(32 * (metrics.densityDpi / 160f));;
        int widthUsed = (int)(410 * (metrics.densityDpi / 160f));

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels, leftPadding = (width-widthUsed)/2;
        findViewById(R.id.scroll).setPadding(leftPadding, 0, 0, 0);

        FrameLayout.LayoutParams params;
        ImageButton button;
        TextView textView;
        Cursor cursor;
        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);

        for(int i=0, size=mGlobals.puzzles.size(); i<size; i++) {
            button = new ImageButton(this);
            button.setBackground(this.getResources().getDrawable(R.drawable.select_arrow));
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClick(v);
                }
            });
            px = (int)(35 * (metrics.densityDpi / 160f));
            params = new FrameLayout.LayoutParams(px, px);
            px = (int)(30 * (metrics.densityDpi / 160f));
            params.setMargins(0, 0, px, 0);
            grid.addView(button,params);

            textView = new TextView(this);
            textView.setBackground(this.getResources().getDrawable(R.drawable.table_background));
            px = (int)(20 * (metrics.densityDpi / 160f));
            textView.setTextSize(px);
            textView.setText(""+(i+1));
            textView.setGravity(Gravity.CENTER);
            px = (int)(50 * (metrics.densityDpi / 160f));
            params = new FrameLayout.LayoutParams(px, px2);
            px = (int)(4 * (metrics.densityDpi / 160f));
            params.setMargins(0,0,px,0);
            grid.addView(textView,params);

            textView = new TextView(this);
            textView.setBackground(this.getResources().getDrawable(R.drawable.table_background));
            textView.setTag("complete"+i);
            px = (int)(20 * (metrics.densityDpi / 160f));
            textView.setTextSize(px);
            cursor = puzzlesAdapter.queryPuzzle(i);
            cursor.moveToFirst();
            textView.setText(cursor.getString(2));
            textView.setGravity(Gravity.CENTER);
            px = (int)(100 * (metrics.densityDpi / 160f));
            params = new FrameLayout.LayoutParams(px, px2);
            px = (int)(4 * (metrics.densityDpi / 160f));
            params.setMargins(0,0,px,0);
            grid.addView(textView,params);

            textView = new TextView(this);
            textView.setBackground(this.getResources().getDrawable(R.drawable.table_background));
            textView.setTag("time"+i);
            px = (int)(20 * (metrics.densityDpi / 160f));
            textView.setTextSize(px);
            int min = cursor.getInt(3), sec = cursor.getInt(4);
            if(min != 0 || sec != 0) textView.setText(min+" min, "+sec+" sec");
            textView.setGravity(Gravity.CENTER);
            px = (int)(200 * (metrics.densityDpi / 160f));
            params = new FrameLayout.LayoutParams(px, px2);
            px = (int)(4 * (metrics.densityDpi / 160f));
            params.setMargins(0,0,px,0);
            grid.addView(textView,params);
        }
    }

    public void onResume() {
        super.onResume();
        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid);
        Cursor cursor;
        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);

        for(int i=0, size=mGlobals.puzzles.size(); i<size; i++) {
            TextView textView;

            textView = (TextView)gridLayout.findViewWithTag("complete" + i);
            cursor = puzzlesAdapter.queryPuzzle(i);
            cursor.moveToFirst();
            textView.setText(cursor.getString(2));
            textView = (TextView)gridLayout.findViewWithTag("time" + i);
            int min = cursor.getInt(3), sec = cursor.getInt(4);
            if(min != 0 || sec != 0) textView.setText(min+" min, "+sec+" sec");
        }

    }

    public void buttonClick(View view) {
        ImageButton button = (ImageButton)view;
        mGlobals.puzzle = mGlobals.puzzles.get(button.getId());
        startActivity(new Intent(this, PlayActivity.class));
    }
}
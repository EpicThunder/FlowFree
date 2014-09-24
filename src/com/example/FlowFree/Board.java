package com.example.FlowFree;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristján on 14.9.2014.
 */
public class Board extends View {

    private int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    boolean legalDraw = false;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid = new Paint();
    private Paint m_paintPath = new Paint();
    private Paint circleColor = new Paint();
    private int currentColor;
    long startTime;

    private Path m_path = new Path();
    private CellPaths m_cellPaths = new CellPaths();

    private List<DotSet> dotSetList = new ArrayList<DotSet>();

    private int xToCol( int x ) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow( int y ) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX( int col ) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY( int row ) {
        return row * m_cellHeight + getPaddingTop() ;
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );

        m_paintPath.setStyle(Paint.Style.STROKE);
        m_paintPath.setStrokeWidth( 32 );
        m_paintPath.setStrokeCap(Paint.Cap.ROUND);
        m_paintPath.setStrokeJoin(Paint.Join.ROUND);
        m_paintPath.setAntiAlias(true);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );

        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;
    }

    @Override
    protected void onDraw( Canvas canvas ) {

        for(int r=0; r<NUM_CELLS; ++r){
            for(int c=0; c<NUM_CELLS; ++c) {
                int x = colToX(c), y = rowToY(r);
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                canvas.drawRect(m_rect, m_paintGrid);
                for(DotSet dotSet:dotSetList) {
                    if(dotSet.hasTheCoordinate(new Coordinate(c, r))) {
                        circleColor.setColor(dotSet.getColor());
                        canvas.drawCircle(x+(m_cellWidth/2), y+(m_cellHeight/2), m_cellWidth/4, circleColor);
                        break;
                    }
                }
            }
        }

        for(int color:m_cellPaths.getSetOfPathColors()) {
            List<Coordinate> colist = m_cellPaths.getCellPath(color).getCoordinates();
            if(colist.size() < 2) continue;
            m_path.reset();
            Coordinate co = colist.get(0);
            m_path.moveTo(colToX(co.getCol()) + m_cellWidth/2, rowToY(co.getRow()) + m_cellHeight/2);
            for( int i=1; i<colist.size(); ++i) {
                co = colist.get(i);
                m_path.lineTo(colToX(co.getCol()) + m_cellWidth/2, rowToY(co.getRow())+ m_cellHeight/2);
            }
            m_paintPath.setColor(color);
            canvas.drawPath(m_path, m_paintPath);
        }
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int) event.getY();
        int c = xToCol(x), r = yToRow(y);

        if (event.getAction() == MotionEvent.ACTION_DOWN && c < NUM_CELLS && r < NUM_CELLS) {
            DotSet dotSet = getDotSetWithLocation(c, r);
            if(dotSet != null) {
                currentColor = dotSet.getColor();
                m_cellPaths.setPath(currentColor);
                m_cellPaths.appendPath(currentColor, new Coordinate(c, r));
                legalDraw = true;
            } else {
                currentColor = m_cellPaths.getColorWithEndLocation(c, r);
                if(currentColor != -1) legalDraw = true;
            }
        }
        else if (legalDraw && event.getAction() == MotionEvent.ACTION_MOVE) {
            if (c < NUM_CELLS && r < NUM_CELLS) {
                m_cellPaths.move(new Coordinate(c, r), currentColor, dotSetList);
                invalidate();
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            legalDraw = false;
            if(m_cellPaths.isComplete(NUM_CELLS)) {
                int minutes=0;
                long timeTaken = System.currentTimeMillis() - startTime; timeTaken *= 0.001;
                while(timeTaken>=60) { minutes++; timeTaken = timeTaken-60; }
                Activity activity = (Activity)this.getContext();
                /*PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(activity);
                Global mGlobal = Global.getInstance();
                puzzlesAdapter.updatePuzzle(mGlobal.puzzles.indexOf(mGlobal.puzzle), "True", minutes, (int)timeTaken);*/
                activity.finish();
            }
        }
        return true;
    }

    public DotSet getDotSetWithLocation(int col, int row) {
        for(DotSet dotSet:dotSetList) {
            if(dotSet.hasTheCoordinate(new Coordinate(col, row))) return dotSet;
        }
        return null;
    }

    public void setPuzzle(Puzzle puzzle) {
        NUM_CELLS = puzzle.getSize(); dotSetList = puzzle.getDotSetList();
        invalidate();
    }

}

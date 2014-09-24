package com.example.FlowFree;

/**
 * Created by Kristján on 14.9.2014.
 */
public class Coordinate {

    private int m_col, m_row;
    Coordinate(int col, int row) {
        m_col = col;
        m_row = row;
    }

    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    public boolean adjacent(Coordinate co) {
        if(m_row == co.getRow() && (m_col-co.getCol() == 1 || m_col-co.getCol() == -1)) return true;
        if(m_col == co.getCol() && (m_row-co.getRow() == 1 || m_row-co.getRow() == -1)) return true;
        return false;
    }

}

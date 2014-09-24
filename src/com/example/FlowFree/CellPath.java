package com.example.FlowFree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristján on 14.9.2014.
 */
public class CellPath {

    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();

    CellPath() {}
    CellPath(CellPath cellPath) { m_path = cellPath.m_path; }

    public void append(Coordinate co) {
        m_path.add(co);
    }

    public boolean hasEndLocation(int c, int r) {
        if(m_path.size() > 0)
            return (m_path.get(m_path.size()-1).getCol() == c && m_path.get(m_path.size()-1).getRow() == r);
        return false;
    }

    public boolean canExtendPath(Coordinate coordinate) {
        if(m_path.size() == 0) return false;
        return m_path.get(m_path.size()-1).adjacent(coordinate);
    }

    public boolean hasCoordinate(Coordinate coordinate) {
        for(Coordinate pathCoordinate:m_path)
            if(pathCoordinate.getCol() == coordinate.getCol() && pathCoordinate.getRow() == coordinate.getRow()) {
                return true;
            }
        return false;
    }

    public boolean cutOffAt(Coordinate co) {
        int index = -1;
        for (int size = m_path.size() - 1; -1 < size; --size)
            if (m_path.get(size).getRow() == co.getRow() && m_path.get(size).getCol() == co.getCol()) {
                index = size;
                break;
            }
        if (index >= 0) {
            for (int size = m_path.size() - 1; index <= size; --size) m_path.remove(size);
            return true;
        }
        return false;
    }

    public void reset() {
        m_path.clear();
    }

    public List<Coordinate> getCoordinates() {
        return m_path;
    }

    public int size() { return m_path.size(); }
}

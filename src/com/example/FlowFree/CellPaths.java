package com.example.FlowFree;

import java.util.*;

/**
 * Created by Kristján on 19.9.2014.
 */
public class CellPaths {
    private HashMap<Integer, CellPath> m_paths = new HashMap<Integer, CellPath>();

    public void setPath(int color) { m_paths.put(color, new CellPath()); }

    public CellPath getCellPath(int c) {
        return m_paths.get(c);
    }

    public int getColorWithEndLocation(int c, int r) {
        for(Integer color:m_paths.keySet()) if(m_paths.get(color).hasEndLocation(c, r)) return color;
        return -1;
    }

    public void move(Coordinate coordinate, int color, List<DotSet> dotSetList) {
        for(DotSet dotSet:dotSetList) if(dotSet.hasTheCoordinate(coordinate) && color != dotSet.getColor()) return;
        if(m_paths.get(color).canExtendPath(coordinate)) {
            CellPath newCellPath;
            for(Integer aColor:m_paths.keySet()) {
                newCellPath = new CellPath(m_paths.get(aColor));
                if(newCellPath.cutOffAt(coordinate)) m_paths.put(aColor, newCellPath);
            }
            newCellPath = m_paths.get(color);
            newCellPath.append(coordinate);
            m_paths.put(color, newCellPath);
        }
    }

    public Set<Integer> getSetOfPathColors() { return m_paths.keySet(); }

    public void appendPath(int color, Coordinate co) {
        CellPath cellPath = new CellPath(m_paths.get(color)); cellPath.append(co); m_paths.put(color, cellPath);
    }

    public boolean isComplete(int cellSize) {
        boolean cellNotComplete;
        Coordinate coordinate;
        for(int c=0; c<cellSize; c++) for(int r=0; r<cellSize; r++) {
            cellNotComplete = true;
            coordinate = new Coordinate(c, r);
            for(CellPath cellPath :m_paths.values()) if(cellPath.hasCoordinate(coordinate)) cellNotComplete = false;
            if(cellNotComplete) return false;
        }

        return true;
    }
}
package com.example.FlowFree;

import java.util.List;

/**
 * Created by Kristján on 15.9.2014.
 */
public class Puzzle {

    private int size;
    private List<DotSet> dotSetList;

    Puzzle(int setSize, List<DotSet> aDotSetList) { size = setSize; dotSetList = aDotSetList; }

    public int getSize() { return size; }

    public List<DotSet> getDotSetList() { return dotSetList; }
}
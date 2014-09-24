package com.example.FlowFree;

/**
 * Created by Kristján on 19.9.2014.
 */
public class DotSet {

    private Coordinate coordinate, coordinate2;
    private int color;
    DotSet(Coordinate setCoordinate, Coordinate setCoordinate2, int setColor) {
        coordinate = setCoordinate; coordinate2 = setCoordinate2; color = setColor;
    }

    public Coordinate getCoordinate() { return coordinate; }

    public Coordinate getCoordinate2() { return coordinate; }

    public boolean hasTheCoordinate(Coordinate aCoordinate) {
        if(coordinate.getRow() == aCoordinate.getRow() && coordinate.getCol() == aCoordinate.getCol()) return true;
        if(coordinate2.getRow() == aCoordinate.getRow() && coordinate2.getCol() == aCoordinate.getCol()) return true;
        return false;
    }

    public int getColor() { return color; }
}
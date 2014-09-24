package com.example.FlowFree;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Global mGlobals = Global.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int padding = size.x/10;
        int heightPadding = size.y/10;
        findViewById(android.R.id.content).setPadding(padding,heightPadding,padding,0);
        findViewById(android.R.id.content).invalidate();

        try {
            List<Pack> packs = new ArrayList<Pack>();
            readPack(getAssets().open("packs/packs.xml"), packs);
            for(Pack pack:packs) readPuzzles(getAssets().open(pack.getFile()));
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        //mGlobals.loadMusic((AudioManager) getSystemService(AUDIO_SERVICE), this);

        /*PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);
        for(int i=0, pSize=mGlobals.puzzles.size(); i<pSize; i++) {
            puzzlesAdapter.insertPuzzle(i, "False", 0, 0);
        }*/
    }

    public void buttonClick(View view) {
        Button button = (Button)view;
        int id = button.getId();
        if(id == R.id.button_play) {
            startActivity(new Intent(this, PuzzleSelect.class));
        }
        else if ( id == R.id.button_settings ) {
            startActivity( new Intent( this, SettingsActivity.class ) );
        }
    }

    private void readPack( InputStream is, List<Pack> packs) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "pack" );
            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName( "name" ).item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName( "description" ).item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName( "file" ).item(0).getFirstChild().getNodeValue();
                    packs.add( new Pack( name, description, file ) );
                }
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void readPuzzles(InputStream is) {
        try {
            List<Integer> colorList = new ArrayList<Integer>();
            colorList.add(Color.BLUE);
            colorList.add(Color.GREEN);
            colorList.add(Color.YELLOW);
            colorList.add(Color.CYAN);
            colorList.add(Color.MAGENTA);
            colorList.add(Color.BLACK);
            colorList.add(Color.WHITE);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "puzzle" );
            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    int size = Integer.parseInt(eNode.getElementsByTagName("size").item(0).getFirstChild().getNodeValue());
                    String flows = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                    List<DotSet> dotSetList = new ArrayList<DotSet>();
                    Coordinate co1, co2;
                    co1 = new Coordinate(Integer.parseInt(flows.substring(1,2)), Integer.parseInt(flows.substring(3,4)));
                    co2 = new Coordinate(Integer.parseInt(flows.substring(5,6)), Integer.parseInt(flows.substring(7,8)));
                    dotSetList.add(new DotSet(co1, co2, Color.RED));
                    flows = flows.substring(9);
                    int count = 0;
                    while(flows.length() > 0) {
                        co1 = new Coordinate(Integer.parseInt(flows.substring(3,4)), Integer.parseInt(flows.substring(5,6)));
                        co2 = new Coordinate(Integer.parseInt(flows.substring(7,8)), Integer.parseInt(flows.substring(9,10)));
                        dotSetList.add(new DotSet(co1, co2, colorList.get(count)));
                        flows = flows.substring(11);
                        count++;
                    }
                    mGlobals.puzzles.add(new Puzzle(size, dotSetList));
                }
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //mGlobals.stopMusic();
        super.onBackPressed();
    }
}

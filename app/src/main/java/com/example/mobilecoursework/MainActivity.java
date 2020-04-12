package com.example.mobilecoursework;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText currentRoadworks, plannedRoad;
    private String result="";
    private TableLayout tl,road,planned;
    private Button roadButton,plannedButton,roadworksSearch,startButton, plannedSearch;
    public LinkedList<CurrentIncidents> roadworks = new LinkedList<>();
    public LinkedList<CurrentIncidents> currentIncidents = new LinkedList<>();
    public  LinkedList<CurrentIncidents> plannedRoadworks = new LinkedList<>();
    private  TextView searchTest;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentRoadworks = findViewById(R.id.currentRoad);
        roadworksSearch = findViewById(R.id.currentRoadworksSearch);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        tl = findViewById(R.id.tableTest);
        roadButton = findViewById(R.id.roadworkButton);
        roadButton.setOnClickListener(this);
        road = findViewById(R.id.tableRoad);
        planned = findViewById(R.id.tablePlannedRoad);
        plannedButton = findViewById(R.id.plannedRoadworkButton);
        plannedButton.setOnClickListener(this);
        plannedSearch = findViewById(R.id.plannedRoadworksSearch);
        plannedSearch.setOnClickListener(this);
        plannedRoad = findViewById(R.id.plannedRoad);
        searchTest = findViewById(R.id.searchtest);
    }

    public void onClick(View aview)
    {
        roadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress(2);
            }
        });
        plannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress(3);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress(1);
            }
        });
        roadworksSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Searching...",Toast.LENGTH_SHORT).show();
                final String query = currentRoadworks.getText().toString();
                RoadWorkSearch(query);
            }
        });
        plannedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Searching...",Toast.LENGTH_SHORT).show();
                final String query = plannedRoad.getText().toString();
                plannedRoadWorkSearch(query);
            }
        });


    }

    public void RoadWorkSearch(String query){
        road.removeAllViews();
        for(CurrentIncidents c : roadworks){
            boolean u = c.getTitle().contains(query.toUpperCase());
            boolean x = c.concatDescription(c.getDescription()).contains(query);

            if(u ==true){

                TextView t = new TextView(getApplicationContext());
                String output = c.getTitle()+" "+ c.concatDescription(c.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(20);
                road.addView(t);
                //searchTest.setText(output);
            }
            else if (x==true){

                TextView t = new TextView(getApplicationContext());
                String output = c.getTitle()+" "+ c.concatDescription(c.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(20);
                //searchTest.setText(output);
                road.addView(t);
            }
            else if (roadworks.indexOf(c) == roadworks.size()-1)
                {

                   TextView t = new TextView(getApplicationContext());
                   String output = "NO CURRENT ROADWORKS FROM SEARCH";
                   t.setText(output);
                   t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                   t.setTextSize(30);
                   t.setTextColor(Color.parseColor("#fa3f3c"));
                   road.addView(t);
                   // searchTest.setText("NO ROADWORKS FROM SEARCH");
            }
        }
    }

    public void plannedRoadWorkSearch(String query){
        planned.removeAllViews();
        for(CurrentIncidents c : plannedRoadworks){
            boolean u = c.getTitle().contains(query.toUpperCase());
            boolean x = c.concatDescription(c.getDescription()).contains(query);
            if(u==true){

                TextView t = new TextView(getApplicationContext());
                String output = c.getTitle()+" "+ c.concatDescription(c.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(20);
                planned.addView(t);
            }
            else if (x==true){

                TextView t = new TextView(getApplicationContext());
                String output = c.getTitle()+" "+ c.concatDescription(c.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(20);

                planned.addView(t);
            }
            else if (plannedRoadworks.indexOf(c) == plannedRoadworks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "NO PLANNED ROADWORKS FROM SEARCH";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(30);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                planned.addView(t);
                // searchTest.setText("NO ROADWORKS FROM SEARCH");
            }
        }
    }

    public void startProgress(int url)
    {
        // Traffic Scotland URLs
        String roadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
        String plannedRoadworks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
        String currentIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

        switch (url){
            case 1:
                // Run network access on a separate thread;
                new Thread(new Task(currentIncidents,1)).start();
                break;
            case 2:
                // Run network access on a separate thread;
                new Thread(new Task(roadworks,2)).start();
                break;
            case 3:
                // Run network access on a separate thread;
                new Thread(new Task(plannedRoadworks,3)).start();
                break;
        }

    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;
        private int c;

        public Task(String aurl,int urlChoice )
        {
            url = aurl;
            c = urlChoice;
        }
        @Override
        public void run()
        {
            final int choice = c;
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            result ="";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                while ((inputLine = in.readLine()) != null)
                {

                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }


            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    parser2 p = new parser2();
                    switch (choice){
                        case 1:


                            currentIncidents.clear();
                            currentIncidents = p.XMLParse(result);
                            if(currentIncidents.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "NO CURRENT INCIDENTS";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                tl.addView(t);
                            }
                            else {
                                tl.removeAllViews();
                                for (int i = 0; i < currentIncidents.size(); i++) {
                                    TextView t = new TextView(getApplicationContext());
                                    String output = currentIncidents.get(i).getTitle() + " - " + currentIncidents.get(i).getDescription();
                                    t.setText(output);
                                    t.setTextSize(20);
                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    tl.addView(t, i);

                                }
                            }
                            //currentIncidents.clear();
                            break;
                        case 2:


                            roadworks.clear();

                            roadworks = p.XMLParse(result);
                            if(roadworks.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "NO CURRENT ROADWORKS";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                road.addView(t);
                            }
                            else {
                                road.removeAllViews();
                                TextView j = new TextView(getApplicationContext());
                                int joutput = roadworks.size();
                                String jtext = "There are currently ";
                                String jtext2 = " roadworks taking place";
                                j.setText(jtext+joutput+jtext2);
                                j.setTextSize(25);
                                j.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                road.addView(j,0);
                                for (int i = 0; i < roadworks.size(); i++) {
                                    TextView t = new TextView(getApplicationContext());
                                    String output = roadworks.get(i).getTitle() + " - " + roadworks.get(i).concatDescription(roadworks.get(i).getDescription());
                                    t.setText(output);
                                    t.setTextSize(20);
                                    long length = roadworks.get(i).RoadworksLength();
                                    if (length > 30 && length < 60) {
                                        t.setBackgroundColor(Color.parseColor("#e6d53c"));
                                    } else if (length < 30) {
                                        t.setBackgroundColor(Color.parseColor("#4bde53"));
                                    } else {
                                        t.setBackgroundColor(Color.parseColor("#fa3f3c"));
                                    }

                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    road.addView(t, i+1);

                                }
                            }
                            //roadworks.clear();
                            break;
                        case 3:
                            plannedRoadworks.clear();
                            plannedRoadworks = p.XMLParse(result);

                            if(plannedRoadworks.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "NO PLANNED ROADWORKS";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                planned.addView(t);
                            }
                            else {
                                planned.removeAllViews();

                                TextView j = new TextView(getApplicationContext());
                                int joutput = plannedRoadworks.size();
                                String jtext = "There are ";
                                String jtext2 = " roadworks planned";
                                j.setText(jtext+joutput+jtext2);
                                j.setTextSize(25);
                                j.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                planned.addView(j,0);
                                for (int i = 0; i < plannedRoadworks.size(); i++) {
                                    TextView t = new TextView(getApplicationContext());
                                    String output = plannedRoadworks.get(i).getTitle() + " - " + plannedRoadworks.get(i).concatDescription(plannedRoadworks.get(i).getDescription());
                                    t.setText(output);
                                    t.setTextSize(20);
                                    long length = plannedRoadworks.get(i).RoadworksLength();
                                    if (length > 30 && length < 60) {
                                        t.setBackgroundColor(Color.parseColor("#e6d53c"));
                                    } else if (length < 30) {
                                        t.setBackgroundColor(Color.parseColor("#4bde53"));
                                    } else {
                                        t.setBackgroundColor(Color.parseColor("#fa3f3c"));
                                    }

                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    planned.addView(t, i+1);

                                }
                                //plannedRoadworks.clear();
                            }
                            break;


                    }

                }
            });
        }

    }

} // End of MainActivity
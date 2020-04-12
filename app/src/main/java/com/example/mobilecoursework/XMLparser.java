package com.example.mobilecoursework;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class XMLparser {

    public LinkedList<CurrentIncidents> dataParse(String dataFeed){
        CurrentIncidents c = null;
        LinkedList<CurrentIncidents> incidents = null;

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataFeed ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        incidents  = new LinkedList<CurrentIncidents>();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        Log.e("MyTag","Item Start Tag found");
                        incidents  = new LinkedList<CurrentIncidents>();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text
                        Log.e("MyTag","title is " + temp);
                        c.setTitle(temp);
                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            Log.e("MyTag","description is " + temp);
                            c.setDescription(temp);
                        }

                }
                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","incident is " + c.toString());
                        incidents.add(c);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = incidents.size();
                        Log.e("MyTag","item size is " + size);
                    }
                }


                // Get the next event
                eventType = xpp.next();

            } // End of while

            //return incidents;
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");

        return incidents;



    }
}

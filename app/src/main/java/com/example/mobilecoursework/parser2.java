package com.example.mobilecoursework;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.LinkedList;

public class parser2 {
    public LinkedList<CurrentIncidents> XMLParse(String xml){
        LinkedList<CurrentIncidents> incidentsList = new LinkedList<>();
        try{
            boolean parsingComplete = false;
            CurrentIncidents incident = null;
            XmlPullParser xmlParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlParser.setInput(new StringReader(xml));
            int eventType = xmlParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && !parsingComplete) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item")) {
                            incident = new CurrentIncidents();
                        } else if (incident != null) {
                            if (name.equalsIgnoreCase("Title")) {
                                incident.setTitle(xmlParser.nextText());
                            } else if (name.equalsIgnoreCase("Description")) {
                                incident.setDescription(xmlParser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item") && incident != null) {
                            incidentsList.add(incident);
                        } else if (name.equalsIgnoreCase("Channel")) {
                            parsingComplete = true;
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return incidentsList;
    }
}

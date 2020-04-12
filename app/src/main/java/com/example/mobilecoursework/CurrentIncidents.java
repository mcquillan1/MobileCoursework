package com.example.mobilecoursework;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentIncidents {
    private String title;
    private String description;
    private String dates;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String t;
        t = title + " " + description;
        return t;
    }

    public String concatDescription(String description) {
        String[] arrOfStr = description.split("<br />", 3);
         String output="";

        for (int i=0;i<arrOfStr.length;i++){
             output = output+ " " +arrOfStr[i]+ " ";
        }
        dates = arrOfStr[0]+""+arrOfStr[1];
        return output;
    }

    public long RoadworksLength(){
        String[] arrOfStr = dates.split(",", 3);
        String end = arrOfStr[2];
        int r = arrOfStr[1].indexOf("End Date");
        String initial = arrOfStr[1].substring(r);
        String initialDate = arrOfStr[1].replace(initial,"");

        DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date endDate = null;
        Date start = null;
        try {
            endDate = format.parse(end);
            start = format.parse(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            long diff = endDate.getTime() - start.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }
}

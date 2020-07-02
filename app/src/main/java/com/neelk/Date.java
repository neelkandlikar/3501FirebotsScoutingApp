package com.neelk.robotics;

import java.util.Comparator;
import java.util.StringTokenizer;

public class Date implements Comparator<Date> {

    private int year;
    private int month;
    private int day;

    // accepts date in yyyy-mm-dd format
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "-");
        year = Integer.parseInt(st.nextToken());
        month = Integer.parseInt(st.nextToken());
        day = Integer.parseInt(st.nextToken());
    }

    public boolean isAfter(Date otherDate) {

        if (year > otherDate.year) return true;
        else if (otherDate.year > year) return false;
        else if (month > otherDate.month) return true;
        else if (otherDate.month > month) return false;
        else if (day > otherDate.day) return true;
        else if (otherDate.day > day) return false;
        return false;
    }


    @Override
    public int compare(Date date, Date other) {
        if (this.isAfter(other)) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
